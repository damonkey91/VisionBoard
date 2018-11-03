package com.example.mrx.visionboardapp.Helpers;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

public class GetImageExternalStorage {
    public static final int REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 53253;

    public static boolean checkReadPermission(Fragment fragment) {
        int readExternalStoragePermission = ContextCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (readExternalStoragePermission != PackageManager.PERMISSION_GRANTED){
            fragment.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION);
            return false;
        }
        return true;
    }
}
