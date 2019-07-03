package com.example.mrx.visionboardapp.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.mrx.visionboardapp.Helpers.CheckPermission;
import com.example.mrx.visionboardapp.R;
import com.example.mrx.visionboardapp.ViewModel.SettingsViewModel;

public class SettingsActivity extends AppCompatActivity {
    private SettingsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
    }

    public void clickedLoadFromFile(View view) {
        if (CheckPermission.checkReadPermission(this))
            viewModel.loadFromFile();
        //Todo: load file and save to shared preferences
    }

    public void clickedSaveToFile(View view) {
        CheckPermission.checkWritePermission(this);
        viewModel.saveToFile();

    }

    public void clickedAddPoints(View view) {
        EditText pointsET = findViewById(R.id.pointsEditText);
        String pointsText = pointsET.getText().toString();
        if (pointsText.isEmpty())
            pointsET.setError(getString(R.string.required));
        else {
            int points = Integer.parseInt(pointsText);
            viewModel.setPoints(points);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            switch (requestCode){
                case CheckPermission.REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION:
                    viewModel.loadFromFile();
                    break;
                case CheckPermission.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION:
                    viewModel.saveToFile();
                    break;
            }
        }
    }
}
