package com.example.mrx.visionboardapp.Objects;

import java.util.ArrayList;

public class Section {
    private String title;
    private ArrayList<Task> taskList;

    public Section(String title, ArrayList<Task> taskList){
        this.title = title;
        this.taskList = taskList;
    }

    public String getTitle() {
        return title;
    }

    //ta bort ?????
    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    //ta bort ?????
    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public void addTask(Task task){
        taskList.add(0 ,task);
    }

    public void removeTask(int positionTask){
        taskList.remove(positionTask);
    }
}
