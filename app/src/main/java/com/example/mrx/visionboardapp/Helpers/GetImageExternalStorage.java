package com.example.mrx.visionboardapp.Helpers;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class GetImageExternalStorage {
    public static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 53253;

    public static boolean checkReadPermission(Activity activity) {
        int readExternalStoragePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (readExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
            return false;
        }
        return true;
    }
}
