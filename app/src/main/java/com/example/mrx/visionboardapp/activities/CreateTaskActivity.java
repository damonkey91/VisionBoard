package com.example.mrx.visionboardapp.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

import com.example.mrx.visionboardapp.Helpers.GsonHandler;
import com.example.mrx.visionboardapp.Objects.TaskItem;
import com.example.mrx.visionboardapp.R;

import java.util.UUID;

public class CreateTaskActivity extends AppCompatActivity {
    private TextInputEditText taskNameET;
    private TextInputEditText taskDescriptionET;
    private NumberPicker pointsNP;
    private Switch recurrentSwitch;
    private boolean editModeActive;
    private UUID taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        taskNameET = findViewById(R.id.activity_text);
        taskDescriptionET = findViewById(R.id.description_text);
        pointsNP = findViewById(R.id.numberPicker);
        recurrentSwitch = findViewById(R.id.recurrent_switch);
        pointsNP.setMinValue(1);
        pointsNP.setMaxValue(3);
        pointsNP.setDisplayedValues(new String[]{"10", "50", "100"});

        editModeActive = isEditMode();
        setupIfEditTask(editModeActive);
        setToolbarTitle(editModeActive);
    }

    public void clickedCreateButton(View view){
        if (validForm()) {
            taskId = (editModeActive && taskId != null) ? taskId : UUID.randomUUID();
            String jsonObj = GsonHandler.convertToString(
                    new TaskItem(
                            getTaskName(),
                            getTaskDescription(),
                            getTaskPoint(),
                            getTaskRecurrent(),
                            taskId
                    )
            );
            Intent intent = getIntent();
            intent.putExtra(WeekdayTasksFragment.TASK_KEY, jsonObj);
            setResult(WeekdayTasksFragment.REQUEST_CODE_CREATE_TASK, intent);
            finish();
        } else {
            taskNameET.setError(getString(R.string.required));
        }
    }

    public void clickedCancelButton(View view){
        finish();
    }

    private String getTaskName(){
        return taskNameET.getText().toString();
    }

    private String getTaskDescription(){
        return taskDescriptionET.getText().toString();
    }

    private boolean getTaskRecurrent(){
        return recurrentSwitch.isChecked();
    }

    private int getTaskPoint(){
        switch (pointsNP.getValue()){
            case 1:
                return 10;
            case 2:
                return 50;
            case 3:
                return 100;
        }
        return 0;
    }

    private boolean validForm(){
        return !getTaskName().isEmpty();
    }

    private void setupIfEditTask(boolean editModeActive){
        Intent intent = getIntent();
        if (editModeActive){
            String jsonTask = intent.getStringExtra(WeekdayTasksFragment.TASK_KEY);
            TaskItem task = GsonHandler.convertToTask(jsonTask);
            pointsNP.setValue(getPickerValue(task.getValue()));
            taskNameET.setText(task.getTitle());
            taskDescriptionET.setText(task.getDescription());
            ((Button)findViewById(R.id.button2)).setText(R.string.save);
            recurrentSwitch.setChecked(task.isRecurrent());
            taskId = task.getId();
        }
    }

    private int getPickerValue(int i) {
        switch (i) {
            case 10:
                return 1;
            case 50:
                return 2;
            case 100:
                return 3;
            default:
                return 1;
        }
    }

    private void setToolbarTitle(boolean isEditMode){
        String title = isEditMode ? getString(R.string.edit_task) : getString(R.string.create_task);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(title);
    }


    private boolean isEditMode(){
        Intent intent = getIntent();
        int requestCode = intent.getIntExtra(WeekdayTasksFragment.REQUEST_CODE, 0);
        return requestCode == WeekdayTasksFragment.REQUEST_CODE_EDIT_TASK;
    }
}
