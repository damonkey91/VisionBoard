package com.example.mrx.visionboardapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.mrx.visionboardapp.Helpers.HandleSharedPreferences;
import com.example.mrx.visionboardapp.Helpers.ReadAndWriteToExternalStorage;

public class SettingsViewModel extends AndroidViewModel {
    private boolean changed = false;
    private String space = "\n---||---\n";

    public SettingsViewModel(@NonNull Application application) {
        super(application);
    }

    public void saveToFile(){
        //Todo: read from shared preferences and save to file
        String rewardlist = HandleSharedPreferences.getStringFromSharedPreferences(HandleSharedPreferences.REWARD_LIST_KEY);
        String weekdaylist = HandleSharedPreferences.getStringFromSharedPreferences(HandleSharedPreferences.WEEKDAY_LIST_KEY);
        String points = "" + HandleSharedPreferences.getPointsFromSharedPreferences();
        String data = rewardlist + space + weekdaylist + space + points + space;
        ReadAndWriteToExternalStorage.writeToFile(data);
    }

    public void loadFromFile(){
        //Todo: load file and save to shared preferences
        ReadAndWriteToExternalStorage.readFromFile();
        setChanged();
    }

    public void setPoints(int points){
        HandleSharedPreferences.savePointsToSharedPreferences(points);
        setChanged();
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged() {
        changed = true;
    }
}
