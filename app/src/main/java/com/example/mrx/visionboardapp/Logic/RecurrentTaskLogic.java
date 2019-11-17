package com.example.mrx.visionboardapp.Logic;

import com.example.mrx.visionboardapp.Helpers.GsonHandler;
import com.example.mrx.visionboardapp.Helpers.HandleSharedPreferences;
import com.example.mrx.visionboardapp.Objects.RecurrentTask;
import com.example.mrx.visionboardapp.Objects.TaskItem;
import com.example.mrx.visionboardapp.Objects.WeekdayList;
import com.example.mrx.visionboardapp.Objects.WeekdaysEnum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class RecurrentTaskLogic {

    private static ArrayList<RecurrentTask> getSavedRecurrentTasks(){
        String jsonObject = HandleSharedPreferences.getStringFromSharedPreferences(HandleSharedPreferences.RECURRENT_TASKS);
        if (jsonObject == null)
            return new ArrayList<RecurrentTask>();
        return GsonHandler.convertToRecurrentTasksArray(jsonObject);
    }

    private static void saveRecurrentTasks(ArrayList<RecurrentTask> recurrentTasks){
        String jsonObject = GsonHandler.convertToString(recurrentTasks);
        HandleSharedPreferences.saveString(jsonObject, HandleSharedPreferences.RECURRENT_TASKS);
    }

    public static void addRecurrentTask(RecurrentTask task){
        ArrayList<RecurrentTask> recurrentTasks = getSavedRecurrentTasks();
        recurrentTasks.add(task);
        saveRecurrentTasks(recurrentTasks);
    }

    public static void removeRecurrentTask(UUID id){
        ArrayList<RecurrentTask> recurrentTasks = getSavedRecurrentTasks();
        RecurrentTask removeTask = findRecurrentTaskById(id, recurrentTasks);
        if (removeTask != null)
            recurrentTasks.remove(removeTask);
        saveRecurrentTasks(recurrentTasks);
    }

    public static void editRecurrentTask(TaskItem task){
        ArrayList<RecurrentTask> recurrentTasks = getSavedRecurrentTasks();
        RecurrentTask editTask = findRecurrentTaskById(task.getId(), recurrentTasks);
        if (editTask != null) {
            recurrentTasks.remove(editTask);
            recurrentTasks.add(editTask);
        }
        saveRecurrentTasks(recurrentTasks);
    }

    public static void insertRecurrentTasksIn(WeekdayList weekdayList){
        if (checkTime()){
            LinkedHashMap<WeekdaysEnum, Integer> weekdaysPositions = weekdayList.getWeekdayPositions();
            ArrayList<RecurrentTask> recurrentTasks = getSavedRecurrentTasks();
            LinkedHashMap<WeekdaysEnum, ArrayList<TaskItem>> sortedTasks = sortRecurrentTasks(recurrentTasks);
            int addedTasks = 0;

            for (Map.Entry<WeekdaysEnum, Integer> entry : weekdaysPositions.entrySet()) {
                WeekdaysEnum key = entry.getKey();
                int position = entry.getValue();
                weekdayList.addTasks(sortedTasks.get(key), position+1+addedTasks);
                addedTasks += sortedTasks.get(key).size();
            }
        }
    }

    private static boolean checkTime(){
        long lastUpdate = HandleSharedPreferences.getRecurrentTaskDateFromSharedPreferences(defaultDate() - SEVENDAYS);
        if (Calendar.getInstance().getTimeInMillis() - lastUpdate >= SEVENDAYS) {
            HandleSharedPreferences.saveRecurrentTaskDateToSharedPreferences(defaultDate());
            return true;
        }
        return false;
    }

    private static LinkedHashMap<WeekdaysEnum, ArrayList<TaskItem>> sortRecurrentTasks(ArrayList<RecurrentTask> recurrentTasks){
        LinkedHashMap<WeekdaysEnum, ArrayList<TaskItem>> sortedTasks = new LinkedHashMap<>();
        setupSortedList(sortedTasks);
        for (RecurrentTask recurrentTask : recurrentTasks)
            sortedTasks.get(recurrentTask.getDay()).add(recurrentTask.getTaskItem());
        return sortedTasks;
    }

    private static void setupSortedList(LinkedHashMap<WeekdaysEnum, ArrayList<TaskItem>> sortedTasks){
        sortedTasks.put(WeekdaysEnum.MONDAY, new ArrayList<TaskItem>());
        sortedTasks.put(WeekdaysEnum.TUESDAY, new ArrayList<TaskItem>());
        sortedTasks.put(WeekdaysEnum.WEDNESDAY, new ArrayList<TaskItem>());
        sortedTasks.put(WeekdaysEnum.THURSDAY, new ArrayList<TaskItem>());
        sortedTasks.put(WeekdaysEnum.FRIDAY, new ArrayList<TaskItem>());
        sortedTasks.put(WeekdaysEnum.SATURDAY, new ArrayList<TaskItem>());
        sortedTasks.put(WeekdaysEnum.SUNDAY, new ArrayList<TaskItem>());
    }

    private static final long SEVENDAYS = 604800000;
    private static long defaultDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        return calendar.getTimeInMillis();
    }

    private static RecurrentTask findRecurrentTaskById(UUID id, ArrayList<RecurrentTask> tasks){
        for (RecurrentTask task : tasks) {
            if (id.equals(task.getId()))
                return task;
        }
        return null;
    }
}
