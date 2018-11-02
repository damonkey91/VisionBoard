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

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }
}
