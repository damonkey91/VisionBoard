package com.example.mrx.visionboardapp.Objects;

public class BackupObject {
    private String rewardString;
    private String taskAndHeaderString;
    private String pointString;

    public BackupObject(String rewardString, String taskAndHeaderString, String pointString) {
        this.rewardString = rewardString;
        this.taskAndHeaderString = taskAndHeaderString;
        this.pointString = pointString;
    }

    public String getRewardString() {
        return rewardString;
    }

    public String getTaskAndHeaderString() {
        return taskAndHeaderString;
    }

    public String getPointString() {
        return pointString;
    }
}
