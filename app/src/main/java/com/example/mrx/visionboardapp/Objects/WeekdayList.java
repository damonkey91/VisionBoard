package com.example.mrx.visionboardapp.Objects;

import com.example.mrx.visionboardapp.Helpers.ResourceGetter;
import com.example.mrx.visionboardapp.R;
import com.example.mrx.visionboardapp.RecyclerViews.MySectionRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class WeekdayList {

    private ArrayList<RecyclerViewItem> taskAndHeaderList;

    public ArrayList getTaskAndHeaderList() {
        if(taskAndHeaderList == null){
            populateWithHeaders();
        }
        return taskAndHeaderList;
    }

    public void setTaskAndHeaderList(ArrayList taskAndHeaderList) {
        this.taskAndHeaderList = taskAndHeaderList;
    }

    public void addTask(TaskItem task, int position){
        taskAndHeaderList.add(position, task);
    }

    public void addTasks(ArrayList<TaskItem> tasks, int position){
        taskAndHeaderList.addAll(position, tasks);
    }

    public void removeTask(int position){
        taskAndHeaderList.remove(position);
    }

    public void removeTask(RecyclerViewItem task){
        taskAndHeaderList.remove(task);
    }

    public void editTask(int position, TaskItem task){
        removeTask(position);
        addTask(task, position);
    }

    public TaskItem getTask(int position){
        return (TaskItem) taskAndHeaderList.get(position);
    }

    public LinkedHashMap<WeekdaysEnum, Integer> getWeekdayPositions(){
        LinkedHashMap<WeekdaysEnum, Integer> headerPositions = new LinkedHashMap<>();
        for (int i = 0; i < taskAndHeaderList.size(); i++) {
            RecyclerViewItem item = taskAndHeaderList.get(i);
            if (item.getType() == MySectionRecyclerViewAdapter.HEADER_TYPE)
                headerPositions.put(((HeaderItem) item).getDay(), i);
        }
        return headerPositions;
    }

    public WeekdaysEnum getDayFor(int position){
        LinkedHashMap<WeekdaysEnum, Integer> headerPositions = getWeekdayPositions();
        WeekdaysEnum day = WeekdaysEnum.MONDAY;
        for (Map.Entry<WeekdaysEnum, Integer> entry : headerPositions.entrySet()) {
            if (entry.getValue() > position)
                return day;
            day = entry.getKey();
        }
        return day; //we will et here if it is sunday
    }

    private void populateWithHeaders() {
        taskAndHeaderList = new ArrayList<>();
        taskAndHeaderList.add(new HeaderItem(ResourceGetter.getString(R.string.monday),WeekdaysEnum.MONDAY));
        taskAndHeaderList.add(new HeaderItem(ResourceGetter.getString(R.string.tuseday), WeekdaysEnum.TUESDAY));
        taskAndHeaderList.add(new HeaderItem(ResourceGetter.getString(R.string.wednesday), WeekdaysEnum.WEDNESDAY));
        taskAndHeaderList.add(new HeaderItem(ResourceGetter.getString(R.string.thursday), WeekdaysEnum.THURSDAY));
        taskAndHeaderList.add(new HeaderItem(ResourceGetter.getString(R.string.friday), WeekdaysEnum.FRIDAY));
        taskAndHeaderList.add(new HeaderItem(ResourceGetter.getString(R.string.saturday), WeekdaysEnum.SATURDAY));
        taskAndHeaderList.add(new HeaderItem(ResourceGetter.getString(R.string.sunday), WeekdaysEnum.SUNDAY));
    }
}
