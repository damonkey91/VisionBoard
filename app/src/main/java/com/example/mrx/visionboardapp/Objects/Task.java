package com.example.mrx.visionboardapp.Objects;

public class Task {
    private String title;
    private String description;
    private int value;

    public Task(String title, String description, int value) {
        this.title = title;
        this.description = description;
        this.value = value;
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
}
