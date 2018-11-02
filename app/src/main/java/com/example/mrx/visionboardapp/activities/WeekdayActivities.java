package com.example.mrx.visionboardapp.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mrx.visionboardapp.Objects.Section;
import com.example.mrx.visionboardapp.R;
import com.example.mrx.visionboardapp.RecyclerViews.WeekdaysSection;
import com.example.mrx.visionboardapp.ViewModels.WeekdayViewModel;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class WeekdayActivities extends AppCompatActivity {

    private WeekdayViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekday_activities);
        viewModel = ViewModelProviders.of(this).get(WeekdayViewModel.class);
        setupListView();
    }

    private void setupListView(){
        RecyclerView listView = findViewById(R.id.listview_weekdays);
        listView.setHasFixedSize(true);
        listView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        int sectionIndex = 0;
        for (Section section : viewModel.getSections()) {
            sectionAdapter.addSection(new WeekdaysSection(section.getTitle(), section.getTaskList(), viewModel, sectionIndex, sectionAdapter));
            sectionIndex++;
        }
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(sectionAdapter);


    }
}
