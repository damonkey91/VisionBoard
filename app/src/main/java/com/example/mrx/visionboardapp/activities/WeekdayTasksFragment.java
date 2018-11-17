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

import com.example.mrx.visionboardapp.Dialogs.ShowTaskDialog;
import com.example.mrx.visionboardapp.Interfaces.IShowTaskInterface;
import com.example.mrx.visionboardapp.Interfaces.IWeekdaysSectionInterface;
import com.example.mrx.visionboardapp.Objects.Section;
import com.example.mrx.visionboardapp.Objects.Task;
import com.example.mrx.visionboardapp.Objects.WeekdayList;
import com.example.mrx.visionboardapp.R;
import com.example.mrx.visionboardapp.RecyclerViews.WeekdaysSection;
import com.example.mrx.visionboardapp.ViewModel.TaskAndPointsViewModel;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class WeekdayTasksFragment extends Fragment implements IWeekdaysSectionInterface, Observer<Integer>, IShowTaskInterface {
    public static final int REQUEST_CODE_CREATE_TASK = 4599;
    public static final int REQUEST_CODE_EDIT_TASK = 4600;
    public static final String REQUEST_CODE = "requestcode";
    public static final String DAY_POSITION_KEY = "daypositionkeyyy";
    public static final String TASK_POSITION_KEY = "taskinsectionpositionkeyyy";
    public static final String TASK_KEY = "taskkeyyy";

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
        intent.putExtra(REQUEST_CODE, REQUEST_CODE_CREATE_TASK);
        intent.putExtra(DAY_POSITION_KEY, sectionNr);
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
    public void clickedOnItem(int adapterPosition, int sectionNr) {
        int taskInSectionPos = sectionAdapter.getPositionInSection(adapterPosition);
        viewModel.setActiveTaskPositions(taskInSectionPos, sectionNr);
        ShowTaskDialog.newInstance(viewModel.getActiveTask(), this).show(getFragmentManager(), "hh");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (intent != null) {
            Task task = new Task(
                    intent.getStringExtra(CreateTaskActivity.TASK_NAME_KEY),
                    intent.getStringExtra(CreateTaskActivity.TASK_DESCRIPTION_KEY),
                    intent.getIntExtra(CreateTaskActivity.POINT_KEY, 0)
            );
            int dayPosition = intent.getIntExtra(DAY_POSITION_KEY, 0);
            int taskPosition = intent.getIntExtra(TASK_POSITION_KEY, 0);

            if (requestCode == REQUEST_CODE_CREATE_TASK) {
                viewModel.addTask(dayPosition, task);
                sectionAdapter.notifyItemInsertedInSection(WeekdayList.dayList.get(dayPosition), 0);
            }else if (requestCode == REQUEST_CODE_EDIT_TASK) {
                viewModel.editTask(dayPosition, taskPosition, task);
                sectionAdapter.notifyItemChangedInSection(WeekdayList.dayList.get(dayPosition), taskPosition);
            }
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

    @Override
    public void deleteActiveTask() {
        int dayPos = viewModel.getActiveDayPosition();
        int taskPos = viewModel.getActiveTaskInSectionPosition();
        viewModel.removeTask(dayPos, taskPos);
        sectionAdapter.notifyItemRemovedFromSection(WeekdayList.dayList.get(dayPos), taskPos);
    }

    @Override
    public void editActiveTask() {
        Task task = viewModel.getActiveTask();
        Intent intent = new Intent(getContext(), CreateTaskActivity.class);
        intent.putExtra(REQUEST_CODE, REQUEST_CODE_EDIT_TASK);
        intent.putExtra(DAY_POSITION_KEY, viewModel.getActiveDayPosition());
        intent.putExtra(TASK_POSITION_KEY, viewModel.getActiveTaskInSectionPosition());
        intent.putExtra(TASK_KEY, new String[]{""+task.getValue(), task.getTitle(), task.getDescription()});
        startActivityForResult(intent, REQUEST_CODE_EDIT_TASK);
    }
}
