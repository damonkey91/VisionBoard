package com.example.mrx.visionboardapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.mrx.visionboardapp.Objects.Section;
import com.example.mrx.visionboardapp.Objects.Task;
import com.example.mrx.visionboardapp.Objects.WeekdayList;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskAndPointsViewModel extends AndroidViewModel {

    private static final String TOTAL_POINTS_KEY= "totalpointskeyyy";

    private Context context;
    private WeekdayList weekdayList;
    private int totalPoints;

    public TaskAndPointsViewModel(Application application){
        super(application);
        if (weekdayList == null){
            weekdayList = new WeekdayList(application);
        }
        context = application;
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
    }

    public void removeTask(int positionDay, int positioTask){
        weekdayList.removeTask(weekdayList.getDay(positionDay), positioTask);
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    private void setTotalPoints() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.totalPoints = prefs.getInt(TOTAL_POINTS_KEY, 0);
    }

    public void addPoints(int addPoints){
        totalPoints += addPoints;
        savePointsToSharedPreferences();
    }

    public void subtractPoints(int subtractPoints){
        totalPoints -= subtractPoints;
        savePointsToSharedPreferences();
    }

    private void savePointsToSharedPreferences(){
        SharedPreferences.Editor prefsEdit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefsEdit.putInt(TOTAL_POINTS_KEY, totalPoints);
        prefsEdit.commit();
    }
}