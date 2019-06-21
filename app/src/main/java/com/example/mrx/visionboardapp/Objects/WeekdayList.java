package com.example.mrx.visionboardapp.Objects;

import com.example.mrx.visionboardapp.Helpers.ResourceGetter;
import com.example.mrx.visionboardapp.R;

import java.util.ArrayList;

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

    private void populateWithHeaders() {
        taskAndHeaderList = new ArrayList<>();
        taskAndHeaderList.add(new HeaderItem(ResourceGetter.getString(R.string.monday)));
        taskAndHeaderList.add(new HeaderItem(ResourceGetter.getString(R.string.tuseday)));
        taskAndHeaderList.add(new HeaderItem(ResourceGetter.getString(R.string.wednesday)));
        taskAndHeaderList.add(new HeaderItem(ResourceGetter.getString(R.string.thursday)));
        taskAndHeaderList.add(new HeaderItem(ResourceGetter.getString(R.string.friday)));
        taskAndHeaderList.add(new HeaderItem(ResourceGetter.getString(R.string.saturday)));
        taskAndHeaderList.add(new HeaderItem(ResourceGetter.getString(R.string.sunday)));
    }
}
