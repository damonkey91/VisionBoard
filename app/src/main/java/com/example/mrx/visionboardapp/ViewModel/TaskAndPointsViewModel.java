package com.example.mrx.visionboardapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.example.mrx.visionboardapp.Helpers.HandleSharedPreferences;
import com.example.mrx.visionboardapp.Objects.Reward;
import com.example.mrx.visionboardapp.Objects.Section;
import com.example.mrx.visionboardapp.Objects.Task;
import com.example.mrx.visionboardapp.Objects.WeekdayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class TaskAndPointsViewModel extends AndroidViewModel {

    private static final String ACTIVE_DAY_POSITION = "ACTIVE_DAY_POSITION";
    private static final String ACTIVE_TASK_IN_SECTION_POSITION = "ACTIVE_TASK_IN_SECTION_POSITION";

    private Context context;
    private WeekdayList weekdayList;
    private MutableLiveData<Integer> totalPoints;
    private ArrayList<Reward> rewardList;
    private HashMap<String, Integer> activeTaskPositions = new HashMap<>();
    private int activeRewardPosition = -1;

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
    }

    public ArrayList<Section> getSections(){
        HashMap<String, Section> sections = weekdayList.getSectionList();
        ArrayList<Section> tempSectionList = new ArrayList<>();
        for (String day : weekdayList.dayList) {
            tempSectionList.add(sections.get(day));
        }
        return tempSectionList;
    }

    public void addTask(int positionDay, Task task){
        weekdayList.addTask(weekdayList.getDay(positionDay), task);
        saveWeekdaylistToSharedPreferences();
    }

    public void editTask(int dayPosition, int taskPosition, Task task) {
        weekdayList.editTask(WeekdayList.dayList.get(dayPosition), taskPosition, task);
        saveWeekdaylistToSharedPreferences();
    }

    public void removeTask(int positionDay, int positionTask){
        weekdayList.removeTask(weekdayList.getDay(positionDay), positionTask);
        saveWeekdaylistToSharedPreferences();
    }

    public Task getTask(int positionDay, int positionTask){
        return weekdayList.getSection(WeekdayList.dayList.get(positionDay)).getTask(positionTask);
    }

    public Task getActiveTask(){
        return getTask(getActiveDayPosition(), getActiveTaskInSectionPosition());
    }

    public LiveData<Integer> getTotalPoints() {
        return totalPoints;
    }

    private void setTotalPoints() {
        totalPoints = new MutableLiveData<>();
        totalPoints.setValue(HandleSharedPreferences.getPointsFromSharedPreferences(context));
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
        HandleSharedPreferences.savePointsToSharedPreferences(totalPoints.getValue(), context);
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
        HandleSharedPreferences.saveObject(weekdayList, context, HandleSharedPreferences.WEEKDAY_LIST_KEY);
    }

    public WeekdayList getWeekdaylistFromSharedPreferences(){
        //TODO: asynkron hämtning
        Object object = HandleSharedPreferences.getObjectFromSharedPreferences(context, HandleSharedPreferences.WEEKDAY_LIST_KEY);
        if (object != null){
            return (WeekdayList) object;
        }
        return new WeekdayList(context);
    }

    public void saveRewardlistToSharedPreferences(){
        //TODO: asynkron sparning
        HandleSharedPreferences.saveObject(rewardList, context, HandleSharedPreferences.REWARD_LIST_KEY);
    }

    public ArrayList<Reward> getRewardlistFromSharedPreferences(){
        //TODO: asynkron hämtning
        Object object = HandleSharedPreferences.getObjectFromSharedPreferences(context, HandleSharedPreferences.REWARD_LIST_KEY);
        if (object != null){
            return (ArrayList<Reward>) object;
        }
        return new ArrayList<>();
    }

    public void setActiveTaskPositions(Integer taskInSectionPos, Integer sectionPos){
        activeTaskPositions.put(ACTIVE_TASK_IN_SECTION_POSITION, taskInSectionPos);
        activeTaskPositions.put(ACTIVE_DAY_POSITION, sectionPos);
    }

    public int getActiveTaskInSectionPosition(){
        return activeTaskPositions.get(ACTIVE_TASK_IN_SECTION_POSITION);
    }

    public int getActiveDayPosition(){
        return activeTaskPositions.get(ACTIVE_DAY_POSITION);
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
