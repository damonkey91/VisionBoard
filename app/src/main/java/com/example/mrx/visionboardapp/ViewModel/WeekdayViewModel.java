package com.example.mrx.visionboardapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.example.mrx.visionboardapp.Objects.Section;
import com.example.mrx.visionboardapp.Objects.Task;
import com.example.mrx.visionboardapp.Objects.WeekdayList;

import java.util.ArrayList;
import java.util.HashMap;

public class WeekdayViewModel extends AndroidViewModel {

    private WeekdayList weekdayList;
    private int totalPoints;

    public WeekdayViewModel(Application application){
        super(application);
        if (weekdayList == null){
            weekdayList = new WeekdayList(application);
        }
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

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void addPoints(int addPoints){
        totalPoints += addPoints;
    }

    public void subtractPoints(int subtractPoints){
        totalPoints -= subtractPoints;
    }
}
