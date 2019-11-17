package com.example.mrx.visionboardapp.Objects;

import com.example.mrx.visionboardapp.RecyclerViews.MySectionRecyclerViewAdapter;

import java.util.UUID;

public class TaskItem extends RecyclerViewItem {
    private String title;
    private String description;
    private int value;
    private boolean recurrent;
    private UUID id;

    public TaskItem(String title, String description, int value, boolean recurrent, UUID id) {
        super(MySectionRecyclerViewAdapter.ITEM_TYPE);
        this.title = title;
        this.description = description;
        this.value = value;
        this.recurrent = recurrent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    public boolean isRecurrent() {
        return recurrent;
    }

    public void setRecurrent(boolean recurrent) {
        this.recurrent = recurrent;
    }

    public UUID getId() {
        return id;
    }
}
