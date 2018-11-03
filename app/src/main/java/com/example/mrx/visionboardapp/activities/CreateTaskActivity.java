package com.example.mrx.visionboardapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.mrx.visionboardapp.R;

public class CreateTaskActivity extends AppCompatActivity {
    public static final String POINT_KEY = "pointsss";
    public static final String TASK_NAME_KEY = "tasknamekeyyy";
    public static final String TASK_DESCRIPTION_KEY = "taskdeskriptionkey";

    private EditText taskNameET;
    private EditText taskDescriptionET;
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
}
