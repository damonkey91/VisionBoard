package com.example.mrx.visionboardapp.Objects;

import java.util.UUID;

public class RecurrentTask {
    private TaskItem taskItem;
    private WeekdaysEnum day;

    public RecurrentTask(TaskItem taskItem, WeekdaysEnum day) {
        this.taskItem = taskItem;
        this.day = day;
    }

    public TaskItem getTaskItem() {
        return taskItem;
    }

    public WeekdaysEnum getDay() {
        return day;
    }

    public UUID getId(){
        return getTaskItem().getId();
    }
}
