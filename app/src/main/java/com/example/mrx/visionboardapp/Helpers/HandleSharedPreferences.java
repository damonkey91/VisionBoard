package com.example.mrx.visionboardapp.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.mrx.visionboardapp.Objects.Reward;
import com.example.mrx.visionboardapp.Objects.WeekdayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HandleSharedPreferences {

    private static final String SHARED_PREFERENCES_KEY = "mysharedpreferences";
    public static final String WEEKDAY_LIST_KEY = "weekdaylistkey";
    public static final String REWARD_LIST_KEY = "rewardlistkey";
    private static final String TOTAL_POINTS_KEY= "totalpointskeyyy";

    public static void savePointsToSharedPreferences(int points, Context context){
        SharedPreferences.Editor prefsEdit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefsEdit.putInt(TOTAL_POINTS_KEY, points);
        prefsEdit.commit();
    }

    public static int getPointsFromSharedPreferences(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(TOTAL_POINTS_KEY, 0);
    }

    public static void saveObject(Object object, Context context, String sharedPrefKey){
        SharedPreferences.Editor spEditor = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).edit();
        String json = convertToString(object);
        spEditor.putString(sharedPrefKey, json);
        spEditor.commit();
    }

    public static Object getObjectFromSharedPreferences(Context context, String sharedPrefsKey){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(sharedPrefsKey, null);
        Object object = null;
        if (json !=null){
            switch (sharedPrefsKey){
                case WEEKDAY_LIST_KEY:
                    object = convertToWeekdayList(json);
                    break;
                case REWARD_LIST_KEY:
                    object = convertToRewardList(json);
                    break;
            }
        }
        return object;
    }

    private static String convertToString(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    private static WeekdayList convertToWeekdayList(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<WeekdayList>() {}.getType();
        return gson.fromJson(json, type);
    }

    private static ArrayList<Reward> convertToRewardList(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Reward>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
