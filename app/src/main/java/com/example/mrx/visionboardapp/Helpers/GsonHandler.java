package com.example.mrx.visionboardapp.Helpers;

import com.example.mrx.visionboardapp.Objects.BackupObject;
import com.example.mrx.visionboardapp.Objects.HeaderItem;
import com.example.mrx.visionboardapp.Objects.RecurrentTask;
import com.example.mrx.visionboardapp.Objects.RecyclerViewItem;
import com.example.mrx.visionboardapp.Objects.Reward;
import com.example.mrx.visionboardapp.Objects.TaskItem;
import com.example.mrx.visionboardapp.Objects.WeekdayList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GsonHandler {

    public static String convertToString(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static ArrayList<Reward> convertToRewardList(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Reward>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public static WeekdayList convertToWeekdayList(String json){
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

    public static BackupObject convertToBackupObject(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, BackupObject.class);
    }

    public static ArrayList<RecurrentTask> convertToRecurrentTasksArray(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<RecurrentTask>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public static TaskItem convertToTask(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<TaskItem>() {}.getType();
        return gson.fromJson(json, type);
    }
}
