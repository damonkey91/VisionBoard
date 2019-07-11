package com.example.mrx.visionboardapp.activities;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mrx.visionboardapp.Helpers.CheckPermission;
import com.example.mrx.visionboardapp.R;
import com.example.mrx.visionboardapp.ViewModel.SettingsViewModel;

public class SettingsActivity extends AppCompatActivity {
    private static final int FILE_PICKER_RESULT_CODE = 21124;
    public static final String SHAREDPREF_CHANGED = "settingsresult";
    private SettingsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        viewModel.isChanged().observe(this, observer);
    }

    public void clickedLoadFromFile(View view) {
        if (CheckPermission.checkReadPermission(this))
            startIntentToPickFile();
        //Todo: load file and save to shared preferences
    }

    public void clickedSaveToFile(View view) {
        if (CheckPermission.checkWritePermission(this))
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
                    startIntentToPickFile();
                    break;
                case CheckPermission.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION:
                    viewModel.saveToFile();
                    break;
            }
        }
    }

    private void startIntentToPickFile(){
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        galleryIntent.setType("*/*");
        startActivityForResult(galleryIntent, FILE_PICKER_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (requestCode == FILE_PICKER_RESULT_CODE && resultCode == Activity.RESULT_OK){
            Uri uri = intent.getData();
            viewModel.loadFromFile(uri);
        }
    }

    final private Observer<Boolean> observer = new Observer<Boolean>() {
        @Override
        public void onChanged(@Nullable Boolean changed) {
            {
                Intent resultIntent = getIntent();
                resultIntent.putExtra(SHAREDPREF_CHANGED, changed);
                setResult(Activity.RESULT_OK, resultIntent);
            }
        }
    };
}
