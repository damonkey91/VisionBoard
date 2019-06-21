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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrx.visionboardapp.Dialogs.ShowTaskDialog;
import com.example.mrx.visionboardapp.Helpers.ItemTouchHelperCallback;
import com.example.mrx.visionboardapp.Interfaces.IItemMovedCallback;
import com.example.mrx.visionboardapp.Interfaces.IShowTaskInterface;
import com.example.mrx.visionboardapp.Interfaces.IWeekdaysSectionInterface;
import com.example.mrx.visionboardapp.Objects.TaskItem;
import com.example.mrx.visionboardapp.R;
import com.example.mrx.visionboardapp.RecyclerViews.MySectionRecyclerViewAdapter;
import com.example.mrx.visionboardapp.ViewModel.TaskAndPointsViewModel;

public class WeekdayTasksFragment extends Fragment implements IWeekdaysSectionInterface, Observer<Integer>, IShowTaskInterface, IItemMovedCallback {
    public static final int REQUEST_CODE_CREATE_TASK = 4599;
    public static final int REQUEST_CODE_EDIT_TASK = 4600;
    public static final String REQUEST_CODE = "requestcode";
    public static final String TASK_POSITION_KEY = "taskinsectionpositionkeyyy";
    public static final String TASK_KEY = "taskkeyyy";

    private View view;
    private TaskAndPointsViewModel viewModel;
    private MenuItem pointMenuItem;
    RecyclerView listView;
    private MySectionRecyclerViewAdapter sectionAdapter;

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
        ItemTouchHelperCallback touchHelperCallback = new ItemTouchHelperCallback(sectionAdapter, this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(listView);
        return view;
    }

    private void setupListView(){
        listView = view.findViewById(R.id.listview_weekdays);
        listView.setHasFixedSize(true);
        listView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        sectionAdapter = new MySectionRecyclerViewAdapter(viewModel.getTaskAndHeaderList(), this);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(sectionAdapter);
    }

    @Override
    public void clickedCreateTask(int position) {
        Intent intent = new Intent(getContext(), CreateTaskActivity.class);
        intent.putExtra(REQUEST_CODE, REQUEST_CODE_CREATE_TASK);
        intent.putExtra(TASK_POSITION_KEY, position);
        startActivityForResult(intent, REQUEST_CODE_CREATE_TASK);
    }

    @Override
    public void clickedFinishedTask(int adapterPosition, int points) {
        viewModel.addPoints(points);
        viewModel.removeTask(adapterPosition);
        sectionAdapter.notifyItemRemoved(adapterPosition);
    }

    @Override
    public void clickedOnItem(int adapterPosition) {
        viewModel.setActiveTaskPosition(adapterPosition);
        ShowTaskDialog.newInstance(viewModel.getActiveTask(), this).show(getFragmentManager(), "hh");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (intent != null) {
            TaskItem task = new TaskItem(
                    intent.getStringExtra(CreateTaskActivity.TASK_NAME_KEY),
                    intent.getStringExtra(CreateTaskActivity.TASK_DESCRIPTION_KEY),
                    intent.getIntExtra(CreateTaskActivity.POINT_KEY, 0)
            );
            int taskPosition = intent.getIntExtra(TASK_POSITION_KEY, 0);

            if (requestCode == REQUEST_CODE_CREATE_TASK) {
                viewModel.addTask(task, taskPosition);
                sectionAdapter.notifyItemInserted(taskPosition);
            }else if (requestCode == REQUEST_CODE_EDIT_TASK) {
                viewModel.editTask(taskPosition, task);
                sectionAdapter.notifyItemChanged(taskPosition);
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
        int taskPos = viewModel.getActiveTaskPosition();
        viewModel.removeTask(taskPos);
        sectionAdapter.notifyItemRemoved(taskPos);
    }

    @Override
    public void editActiveTask() {
        TaskItem task = viewModel.getActiveTask();
        Intent intent = new Intent(getContext(), CreateTaskActivity.class);
        intent.putExtra(REQUEST_CODE, REQUEST_CODE_EDIT_TASK);
        intent.putExtra(TASK_POSITION_KEY, viewModel.getActiveTaskPosition());
        intent.putExtra(TASK_KEY, new String[]{""+task.getValue(), task.getTitle(), task.getDescription()});
        startActivityForResult(intent, REQUEST_CODE_EDIT_TASK);
    }

    @Override
    public void itemMovedCallback() {

    }
}
