package com.example.mrx.visionboardapp.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class HandleSharedPreferences {

    private static final String SHARED_PREFERENCES_KEY = "mysharedpreferences";
    public static final String WEEKDAY_LIST_KEY = "weekdaylistkey";
    public static final String REWARD_LIST_KEY = "rewardlistkey";
    private static final String TOTAL_POINTS_KEY= "totalpointskeyyy";

    public static void savePointsToSharedPreferences(int points){
        SharedPreferences.Editor prefsEdit = PreferenceManager.getDefaultSharedPreferences(AppContextGetter.getContext()).edit();
        prefsEdit.putInt(TOTAL_POINTS_KEY, points);
        prefsEdit.commit();
    }

    public static int getPointsFromSharedPreferences(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AppContextGetter.getContext());
        return prefs.getInt(TOTAL_POINTS_KEY, 0);
    }

    public static void saveString(String json, String sharedPrefKey){
        Context context = AppContextGetter.getContext();
        SharedPreferences.Editor spEditor = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).edit();
        spEditor.putString(sharedPrefKey, json);
        spEditor.commit();
    }

    public static String getStringFromSharedPreferences(String sharedPrefsKey){
        Context context = AppContextGetter.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(sharedPrefsKey, null);
        return json;
    }


}
