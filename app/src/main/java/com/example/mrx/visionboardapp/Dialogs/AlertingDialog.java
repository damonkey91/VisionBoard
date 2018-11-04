package com.example.mrx.visionboardapp.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.mrx.visionboardapp.R;

public class AlertingDialog extends DialogFragment {

    private String message;

    public static AlertingDialog newInstance(String message){
        AlertingDialog alertingDialog = new AlertingDialog();
        alertingDialog.message = message;
        return alertingDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        Dialog dialog = builder
                .setMessage(message)
                .setNegativeButton(R.string.cancel, null)
                .create();
        return dialog;
    }
}
