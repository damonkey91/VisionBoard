package com.example.mrx.visionboardapp.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

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
        pointsNP.setDisplayedValues(new String[]{"10", "50", "100"});

        boolean isEditMode = setupIfEditTask();
        setToolbarTitle(isEditMode);
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

    private boolean setupIfEditTask(){
        Intent intent = getIntent();
        int requestCode = intent.getIntExtra(WeekdayTasksFragment.REQUEST_CODE, 0);
        if (requestCode == WeekdayTasksFragment.REQUEST_CODE_EDIT_TASK){
            String[] task = intent.getStringArrayExtra(WeekdayTasksFragment.TASK_KEY);
            pointsNP.setValue(getPickerValue(Integer.parseInt(task[0])));
            taskNameET.setText(task[1]);
            taskDescriptionET.setText(task[2]);
            ((Button)findViewById(R.id.button2)).setText(R.string.save);
            return true;
        }
        return false;
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
                return 0;
        }
    }

    private void setToolbarTitle(boolean isEditMode){
        String title = isEditMode ? getString(R.string.edit_task) : getString(R.string.create_task);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(title);
    }
}
