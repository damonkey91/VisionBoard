package com.example.mrx.visionboardapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.mrx.visionboardapp.Objects.Section;
import com.example.mrx.visionboardapp.Objects.Task;
import com.example.mrx.visionboardapp.Objects.WeekdayList;
import com.example.mrx.visionboardapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class WeekdayViewModel extends AndroidViewModel {

    private static final String IMAGE_DIRECTORY= "imageDir";
    private static final String IMAGE_FILE_NAME = "choosenpic";

    private WeekdayList weekdayList;
    private int totalPoints;
    private Context context;

    public WeekdayViewModel(Application application){
        super(application);
        context = application;
        if (weekdayList == null){
            weekdayList = new WeekdayList(application);
        }
    }

    public ArrayList<Section> getSections(){
        HashMap<String, Section> sections = weekdayList.getSectionList();
        ArrayList<Section> tempSectionList = new ArrayList<>();
        for (String day : weekdayList.dayList) {
            tempSectionList.add(sections.get(day));
        }
        return tempSectionList;
    }

    public void addTask(int positionDay, Task task){
        weekdayList.addTask(weekdayList.getDay(positionDay), task);
    }

    public void removeTask(int positionDay, int positioTask){
        weekdayList.removeTask(weekdayList.getDay(positionDay), positioTask);
    }

    public Bitmap getImage(){
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), android.R.drawable.picture_frame);
        try {
            FileInputStream inputStream = new FileInputStream(getImagePath());
            image = BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, context.getString(R.string.couldnt_find_file), Toast.LENGTH_SHORT).show();
        }
        return image;
    }

    public void saveBitmap(Bitmap bitmap){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(getImagePath());
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getImagePath(){
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE);
        return new File(directory,IMAGE_FILE_NAME);
    }
    //Todo: image ska vara i en separat viewmodel
    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void addPoints(int addPoints){
        totalPoints += addPoints;
    }

    public void subtractPoints(int subtractPoints){
        totalPoints -= subtractPoints;
    }
}
