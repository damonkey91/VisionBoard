package com.example.mrx.visionboardapp.activities;

import android.arch.lifecycle.Observer;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrx.visionboardapp.Interfaces.IWeekdaysSectionInterface;
import com.example.mrx.visionboardapp.Objects.Section;
import com.example.mrx.visionboardapp.Objects.Task;
import com.example.mrx.visionboardapp.Objects.WeekdayList;
import com.example.mrx.visionboardapp.R;
import com.example.mrx.visionboardapp.RecyclerViews.WeekdaysSection;
import com.example.mrx.visionboardapp.ViewModel.TaskAndPointsViewModel;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class WeekdayTasksFragment extends Fragment implements IWeekdaysSectionInterface, Observer<Integer> {
    public static final int REQUEST_CODE_CREATE_TASK = 4599;
    public static final String POSITION_KEY = "positionkeyyy";

    private View view;
    private TaskAndPointsViewModel viewModel;
    private MenuItem pointMenuItem;
    SectionedRecyclerViewAdapter sectionAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(getActivity()).get(TaskAndPointsViewModel.class);
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
            sectionAdapter.addSection(WeekdayList.dayList.get(sectionIndex), new WeekdaysSection(section.getTitle(), section.getTaskList(), sectionIndex, this));
            sectionIndex++;
        }
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(sectionAdapter);
    }

    @Override
    public void clickedCreateTask(int sectionNr) {
        Intent intent = new Intent(getContext(), CreateTaskActivity.class);
        intent.putExtra(POSITION_KEY, sectionNr);
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
            int position = intent.getIntExtra(POSITION_KEY, 0);
            viewModel.addTask(position, new Task(taskName, taskDesc, taskPoint));
            sectionAdapter.notifyItemInsertedInSection(WeekdayList.dayList.get(position), 0);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.points_menu, menu);
        pointMenuItem = menu.findItem(R.id.actionbar_points);
        viewModel.getTotalPoints().observe(this, this);
        pointMenuItem.setTitle(viewModel.getTotalPoints().getValue()+"$");
    }

    @Override
    public void onChanged(@Nullable Integer integer) {
        pointMenuItem.setTitle(integer + "$");
    }
}
