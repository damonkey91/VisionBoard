package com.example.mrx.visionboardapp.Objects;

import android.content.Context;

import com.example.mrx.visionboardapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WeekdayList {

    public static final String MONDAY_KEY = "monday";
    public static final String TUESDAY_KEY = "tuesday";
    public static final String WEDNESDAY_KEY = "wednesday";
    public static final String THURSDAY_KEY = "thursday";
    public static final String FRIDAY_KEY = "friday";
    public static final String SATURDAY_KEY = "saturday";
    public static final String SUNDAY_KEY = "sunday";
    public static final List<String> dayList = Arrays.asList(MONDAY_KEY, TUESDAY_KEY, WEDNESDAY_KEY, THURSDAY_KEY, FRIDAY_KEY, SATURDAY_KEY, SUNDAY_KEY);

    private HashMap<String, Section> sectionList;

    public WeekdayList(Context context){
        sectionList = new HashMap<>();
        sectionList.put(MONDAY_KEY, new Section(context.getString(R.string.monday), new ArrayList<Task>()));
        sectionList.put(TUESDAY_KEY, new Section(context.getString(R.string.tuseday), new ArrayList<Task>()));
        sectionList.put(WEDNESDAY_KEY, new Section(context.getString(R.string.wednesday), new ArrayList<Task>()));
        sectionList.put(THURSDAY_KEY, new Section(context.getString(R.string.thursday), new ArrayList<Task>()));
        sectionList.put(FRIDAY_KEY, new Section(context.getString(R.string.friday), new ArrayList<Task>()));
        sectionList.put(SATURDAY_KEY, new Section(context.getString(R.string.saturday), new ArrayList<Task>()));
        sectionList.put(SUNDAY_KEY, new Section(context.getString(R.string.sunday), new ArrayList<Task>()));
    }

    public HashMap<String, Section> getSectionList() {
        return sectionList;
    }

    public Section getSection(String day){
        return sectionList.get(day);
    }

    public void setSection(String day, Section section){
        sectionList.put(day, section);
    }

    public void addTask(String day, Task task){
        sectionList.get(day).addTask(task);
    }

    public void editTask(String day, int taskPos, Task task){
        sectionList.get(day).editTask(taskPos, task);
    }

    public void removeTask(String day, int positionTask){
        sectionList.get(day).removeTask(positionTask);
    }

    public String getDay(int positionDay){
        return dayList.get(positionDay);
    }

}
