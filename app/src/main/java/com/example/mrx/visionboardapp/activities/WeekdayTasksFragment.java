package com.example.mrx.visionboardapp.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrx.visionboardapp.Interfaces.IWeekdaysSectionInterface;
import com.example.mrx.visionboardapp.Objects.Section;
import com.example.mrx.visionboardapp.Objects.Task;
import com.example.mrx.visionboardapp.Objects.WeekdayList;
import com.example.mrx.visionboardapp.R;
import com.example.mrx.visionboardapp.RecyclerViews.WeekdaysSection;
import com.example.mrx.visionboardapp.ViewModel.WeekdayViewModel;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class WeekdayTasksFragment extends Fragment implements IWeekdaysSectionInterface {
    public static final int REQUEST_CODE_CREATE_TASK = 4599;
    public static final String POSITION_KEY = "positionkeyyy";

    private View view;
    private WeekdayViewModel viewModel;
    SectionedRecyclerViewAdapter sectionAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(WeekdayViewModel.class);
        view = inflater.inflate(R.layout.fragment_weekday_activities, container, false);
        setupListView();
        return view;
    }

    private void setupListView(){
        RecyclerView listView = view.findViewById(R.id.listview_weekdays);
        listView.setHasFixedSize(true);
        listView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        sectionAdapter = new SectionedRecyclerViewAdapter();

        int sectionIndex = 0;
        for (Section section : viewModel.getSections()) {
            String day = WeekdayList.dayList.get(sectionIndex);
            String title = section.getTitle();
            sectionAdapter.addSection(WeekdayList.dayList.get(sectionIndex), new WeekdaysSection(section.getTitle(), section.getTaskList(), sectionIndex, this));
            sectionIndex++;
        }
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(sectionAdapter);
    }

    @Override
    public void clickedCreateTask() {
        Intent intent = new Intent(getContext(), CreateTaskActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CREATE_TASK);
    }

    @Override
    public void clickedFinishedTask(int adapterPosition, int sectionNr, io.github.luizgrp.sectionedrecyclerviewadapter.Section section, int points) {
        int position = sectionAdapter.getPositionInSection(adapterPosition);
        viewModel.addPoints(points);
        viewModel.removeTask(sectionNr, position);
        sectionAdapter.notifyItemRemovedFromSection(section, position);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (requestCode == REQUEST_CODE_CREATE_TASK && intent != null) {
            String taskName = intent.getStringExtra(CreateTaskActivity.TASK_NAME_KEY);
            String taskDesc = intent.getStringExtra(CreateTaskActivity.TASK_DESCRIPTION_KEY);
            int taskPoint = intent.getIntExtra(CreateTaskActivity.POINT_KEY, 0);
            viewModel.addTask(1, new Task(taskName, taskDesc, taskPoint));
            sectionAdapter.notifyItemInsertedInSection(WeekdayList.MONDAY_KEY, 0);
        }
    }
}
