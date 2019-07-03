package com.example.mrx.visionboardapp.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.mrx.visionboardapp.Objects.HeaderItem;
import com.example.mrx.visionboardapp.Objects.RecyclerViewItem;
import com.example.mrx.visionboardapp.Objects.Reward;
import com.example.mrx.visionboardapp.Objects.TaskItem;
import com.example.mrx.visionboardapp.Objects.WeekdayList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

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

    public static void saveObject(Object object, String sharedPrefKey){
        Context context = AppContextGetter.getContext();
        SharedPreferences.Editor spEditor = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).edit();
        String json = convertToString(object);
        spEditor.putString(sharedPrefKey, json);
        spEditor.commit();
    }

    public static Object getObjectFromSharedPreferences(String sharedPrefsKey){
        String json = getStringFromSharedPreferences(sharedPrefsKey);
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

    public static String getStringFromSharedPreferences(String sharedPrefsKey){
        Context context = AppContextGetter.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(sharedPrefsKey, null);
        return json;
    }

    private static String convertToString(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    private static WeekdayList convertToWeekdayList(String json){
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("taskAndHeaderList").getAsJsonArray();
        ArrayList<RecyclerViewItem> list = new ArrayList<>();
        for (JsonElement jsonElemement : jsonArray) {
            JsonObject recyclerViewItem = jsonElemement.getAsJsonObject();
            int type = recyclerViewItem.get("type").getAsInt();
            if (type == 2){
                list.add(gson.fromJson(jsonElemement, TaskItem.class));
            }else {
                list.add(gson.fromJson(jsonElemement, HeaderItem.class));
            }
        }
        WeekdayList weekdayList = new WeekdayList();
        weekdayList.setTaskAndHeaderList(list);
        return weekdayList;
    }

    private static ArrayList<Reward> convertToRewardList(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Reward>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
