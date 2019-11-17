package com.example.mrx.visionboardapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.example.mrx.visionboardapp.Helpers.GsonHandler;
import com.example.mrx.visionboardapp.Helpers.HandleSharedPreferences;
import com.example.mrx.visionboardapp.Logic.RecurrentTaskLogic;
import com.example.mrx.visionboardapp.Objects.RecurrentTask;
import com.example.mrx.visionboardapp.Objects.RecyclerViewItem;
import com.example.mrx.visionboardapp.Objects.Reward;
import com.example.mrx.visionboardapp.Objects.TaskItem;
import com.example.mrx.visionboardapp.Objects.WeekdayList;

import java.util.ArrayList;

public class TaskAndPointsViewModel extends AndroidViewModel {

    private static final String ACTIVE_DAY_POSITION = "ACTIVE_DAY_POSITION";
    private static final String ACTIVE_TASK_IN_SECTION_POSITION = "ACTIVE_TASK_IN_SECTION_POSITION";
    private static final int NOT_SET = -1;

    private Context context;
    private WeekdayList weekdayList;
    private MutableLiveData<Integer> totalPoints;
    private ArrayList<Reward> rewardList;
    private int activeTaskPosition = NOT_SET;
    private int activeRewardPosition = NOT_SET;

    public TaskAndPointsViewModel(Application application){
        super(application);
        context = application;
        if (weekdayList == null){
            weekdayList = getWeekdaylistFromSharedPreferences();
        }
        if (rewardList == null){
            rewardList = getRewardlistFromSharedPreferences();
        }
        setTotalPoints();
        RecurrentTaskLogic.insertRecurrentTasksIn(weekdayList);
    }

    public void addTask(TaskItem task, int position){
        weekdayList.addTask(task, position);
        saveWeekdaylistToSharedPreferences();
        if(task.isRecurrent())
            RecurrentTaskLogic.addRecurrentTask(new RecurrentTask(task, weekdayList.getDayFor(position)));
    }

    public void editTask(int taskPosition, TaskItem task) {
        if (task.isRecurrent() != weekdayList.getTask(taskPosition).isRecurrent()) {
            if (task.isRecurrent())
                RecurrentTaskLogic.addRecurrentTask(new RecurrentTask(task, weekdayList.getDayFor(taskPosition)));
            else
                RecurrentTaskLogic.removeRecurrentTask(task.getId());
        }
        if (task.isRecurrent() && task.isRecurrent() == weekdayList.getTask(taskPosition).isRecurrent())
            RecurrentTaskLogic.editRecurrentTask(task);
        weekdayList.editTask(taskPosition, task);
        saveWeekdaylistToSharedPreferences();
    }

    public void removeTask(int positionTask){
        weekdayList.removeTask(positionTask);
        saveWeekdaylistToSharedPreferences();
    }

    public TaskItem getTask(int position){
        return weekdayList.getTask(position);
    }

    public TaskItem getActiveTask(){
        return getTask(getActiveTaskPosition());
    }

    public ArrayList<RecyclerViewItem> getTaskAndHeaderList(){
        return weekdayList.getTaskAndHeaderList();
    }

    public LiveData<Integer> getTotalPoints() {
        return totalPoints;
    }

    private void setTotalPoints() {
        totalPoints = new MutableLiveData<>();
        totalPoints.setValue(HandleSharedPreferences.getPointsFromSharedPreferences());
    }

    public void addPoints(int addPoints){
        totalPoints.setValue(totalPoints.getValue()+addPoints);
        savePointsToSharedPreferences();
    }

    public void subtractPointsAndRemoveReward(int rewardPosition){
        totalPoints.setValue(totalPoints.getValue() - rewardList.get(rewardPosition).getRewardPrice());
        rewardList.remove(rewardPosition);
        savePointsToSharedPreferences();
        saveRewardlistToSharedPreferences();
    }

    private void savePointsToSharedPreferences(){
        HandleSharedPreferences.savePointsToSharedPreferences(totalPoints.getValue());
    }

    public boolean gotEnoughPoints(int rewardPosition){
        return rewardList.get(rewardPosition).getRewardPrice() <= totalPoints.getValue();
    }

    public ArrayList<Reward> getRewardList() {
        return rewardList;
    }

    public void addToRewardList(Reward reward){
        rewardList.add(0, reward);
        saveRewardlistToSharedPreferences();
    }

    //Todo: Bör egentligen vara en sqlite database istället för sharedpreferences

    public void saveWeekdaylistToSharedPreferences(){
        //TODO: asynkron sparning
        String string = GsonHandler.convertToString(weekdayList);
        HandleSharedPreferences.saveString(string, HandleSharedPreferences.WEEKDAY_LIST_KEY);
    }

    public WeekdayList getWeekdaylistFromSharedPreferences(){
        //TODO: asynkron hämtning
        String string = HandleSharedPreferences.getStringFromSharedPreferences(HandleSharedPreferences.WEEKDAY_LIST_KEY);

        if (string != null){
            WeekdayList weekdayList = GsonHandler.convertToWeekdayList(string);
            return weekdayList;
        }
        return new WeekdayList();
    }

    public void saveRewardlistToSharedPreferences(){
        //TODO: asynkron sparning
        String string = GsonHandler.convertToString(rewardList);
        HandleSharedPreferences.saveString(string, HandleSharedPreferences.REWARD_LIST_KEY);
    }

    public ArrayList<Reward> getRewardlistFromSharedPreferences(){
        //TODO: asynkron hämtning
        String string = HandleSharedPreferences.getStringFromSharedPreferences(HandleSharedPreferences.REWARD_LIST_KEY);
        ArrayList<Reward> list = GsonHandler.convertToRewardList(string);
        if (list != null){
            return list;
        }
        return new ArrayList<>();
    }

    public void setActiveTaskPosition(int position){
        activeTaskPosition = position;
    }

    public int getActiveTaskPosition(){
        return activeTaskPosition;
    }

    public void setActiveRewardPosition(int position){
        activeRewardPosition = position;
    }

    public int getActiveRewardPosition(){
       return activeRewardPosition;
    }

    public void editReward(int position, Reward reward) {
        rewardList.remove(position);
        rewardList.add(position, reward);
        saveRewardlistToSharedPreferences();
    }

    public void moveRewardItem(){
        saveRewardlistToSharedPreferences();
    }
}
