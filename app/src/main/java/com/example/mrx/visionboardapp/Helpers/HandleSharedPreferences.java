package com.example.mrx.visionboardapp.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HandleSharedPreferences {

    private static final String SHARED_PREFERENCES_KEY = "mysharedpreferences";
    public static final String WEEKDAY_LIST_KEY = "weekdaylistkey";
    public static final String REWARD_LIST_KEY = "rewardlistkey";
    private static final String TOTAL_POINTS_KEY= "totalpointskeyyy";
    private static final String RECURRENT_TASKS_DATE = "thelastdateweaddedrecurrenttasks";
    public static final String RECURRENT_TASKS = "recurrenttaskarray";

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

    public static void saveRecurrentTaskDateToSharedPreferences(long date){
        Context context = AppContextGetter.getContext();
        SharedPreferences.Editor sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).edit();
        sharedPreferences.putLong(RECURRENT_TASKS_DATE, date);
        sharedPreferences.commit();
    }

    public static long getRecurrentTaskDateFromSharedPreferences(long defaultValue){
        Context context = AppContextGetter.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);

        return sharedPreferences.getLong(RECURRENT_TASKS_DATE, defaultValue);
    }
}
