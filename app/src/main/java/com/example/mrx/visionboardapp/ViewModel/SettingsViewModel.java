package com.example.mrx.visionboardapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.mrx.visionboardapp.Helpers.FileReadAsync;
import com.example.mrx.visionboardapp.Helpers.GsonHandler;
import com.example.mrx.visionboardapp.Helpers.HandleSharedPreferences;
import com.example.mrx.visionboardapp.Helpers.ReadAndWriteToExternalStorage;
import com.example.mrx.visionboardapp.Objects.BackupObject;

public class SettingsViewModel extends AndroidViewModel implements FileReadAsync.IFileReadCallback {
    private MutableLiveData<Boolean> changed = new MutableLiveData<>();

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        changed.setValue(false);
    }

    public void saveToFile(){
        //Todo: read from shared preferences and save to file
        String rewardlist = HandleSharedPreferences.getStringFromSharedPreferences(HandleSharedPreferences.REWARD_LIST_KEY);
        String weekdaylist = HandleSharedPreferences.getStringFromSharedPreferences(HandleSharedPreferences.WEEKDAY_LIST_KEY);
        String points = "" + HandleSharedPreferences.getPointsFromSharedPreferences();
        BackupObject backupObject = new BackupObject(rewardlist, weekdaylist, points);
        ReadAndWriteToExternalStorage.writeToFile(GsonHandler.convertToString(backupObject));
    }

    public void loadFromFile(Uri uri){
        //Todo: load file and save to shared preferences
        ReadAndWriteToExternalStorage.readFromFile(this ,uri);
        setChanged();
    }

    public void setPoints(int points){
        HandleSharedPreferences.savePointsToSharedPreferences(points);
        setChanged();
    }

    public MutableLiveData<Boolean> isChanged() {
        return changed;
    }

    public void setChanged() {
        changed.setValue(true);
    }

    @Override
    public void fileRead(BackupObject backupObject) {
        HandleSharedPreferences.saveString(backupObject.getRewardString(), HandleSharedPreferences.REWARD_LIST_KEY);
        HandleSharedPreferences.saveString(backupObject.getTaskAndHeaderString(), HandleSharedPreferences.WEEKDAY_LIST_KEY);
        HandleSharedPreferences.savePointsToSharedPreferences(Integer.parseInt(backupObject.getPointString()));
    }
}
