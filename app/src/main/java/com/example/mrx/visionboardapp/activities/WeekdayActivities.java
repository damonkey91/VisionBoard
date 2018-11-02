package com.example.mrx.visionboardapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mrx.visionboardapp.Objects.Section;
import com.example.mrx.visionboardapp.Objects.Task;
import com.example.mrx.visionboardapp.R;
import com.example.mrx.visionboardapp.RecyclerViews.WeekdaysSection;

import java.util.ArrayList;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class WeekdayActivities extends AppCompatActivity {

    private ArrayList<Section> sectionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekday_activities);

        //Dummydata
        ArrayList<Task> tempList = new ArrayList<>();
        tempList.add(new Task("Springa", "Springa 6 km", 1));
        tempList.add(new Task("Handla", "Springa 6 km", 1));
        tempList.add(new Task("Runka", "Springa 6 km", 1));
        tempList.add(new Task("Kissa", "Springa 6 km", 1));
        sectionList = new ArrayList<>();
        sectionList.add(new Section("Måndag", tempList));
        tempList.add(new Task("Kissdag", "Springa 6 km", 1));
        sectionList.add(new Section("Tisdag", tempList));

        setupListView();
    }

    private void setupListView(){
        RecyclerView listView = findViewById(R.id.listview_weekdays);
        listView.setHasFixedSize(true);
        listView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        for (Section section : sectionList) {
            sectionAdapter.addSection(new WeekdaysSection(section.getTitle(), section.getTaskList()));
        }
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(sectionAdapter);

    }
}
