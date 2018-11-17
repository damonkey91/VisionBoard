package com.example.mrx.visionboardapp.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.mrx.visionboardapp.R;

public class CreateTaskActivity extends AppCompatActivity {
    public static final String POINT_KEY = "pointsss";
    public static final String TASK_NAME_KEY = "tasknamekeyyy";
    public static final String TASK_DESCRIPTION_KEY = "taskdeskriptionkey";

    private TextInputEditText taskNameET;
    private TextInputEditText taskDescriptionET;
    private NumberPicker pointsNP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        taskNameET = findViewById(R.id.activity_text);
        taskDescriptionET = findViewById(R.id.description_text);
        pointsNP = findViewById(R.id.numberPicker);
        pointsNP.setMinValue(1);
        pointsNP.setMaxValue(3);
        pointsNP.setDisplayedValues(new String[]{"1", "5", "10"});

        setupIfEditTask();
    }

    public void clickedCreateButton(View view){
        if (validForm()) {
            Intent intent = getIntent();
            intent.putExtra(POINT_KEY, getTaskPoint());
            intent.putExtra(TASK_NAME_KEY, getTaskName());
            intent.putExtra(TASK_DESCRIPTION_KEY, getTaskDescription());
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

    private int getTaskPoint(){
        switch (pointsNP.getValue()){
            case 1:
                return 1;
            case 2:
                return 5;
            case 3:
                return 10;
        }
        return 0;
    }

    private boolean validForm(){
        return !getTaskName().isEmpty();
    }

    private void setupIfEditTask(){
        Intent intent = getIntent();
        int requestCode = intent.getIntExtra(WeekdayTasksFragment.REQUEST_CODE, 0);
        if (requestCode == WeekdayTasksFragment.REQUEST_CODE_EDIT_TASK){
            String[] task = intent.getStringArrayExtra(WeekdayTasksFragment.TASK_KEY);
            pointsNP.setValue(getPickerValue(Integer.parseInt(task[0])));
            taskNameET.setText(task[1]);
            taskDescriptionET.setText(task[2]);
            ((Button)findViewById(R.id.button2)).setText(R.string.save);
        }
    }

    private int getPickerValue(int i) {
        switch (i) {
            case 5:
                return 2;
            case 10:
                return 3;
            default:
                return 1;
        }
    }
}
