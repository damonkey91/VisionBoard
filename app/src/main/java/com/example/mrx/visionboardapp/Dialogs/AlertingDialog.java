package com.example.mrx.visionboardapp.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.mrx.visionboardapp.Interfaces.INotEnoughMoneyInterface;
import com.example.mrx.visionboardapp.R;

public class AlertingDialog extends DialogFragment {

    private String message;
    private INotEnoughMoneyInterface callback;
    private int itemPosition;

    public static AlertingDialog newInstance(INotEnoughMoneyInterface callback, String message, int itemPosition){
        AlertingDialog alertingDialog = new AlertingDialog();
        alertingDialog.message = message;
        alertingDialog.callback = callback;
        alertingDialog.itemPosition = itemPosition;
        return alertingDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        Dialog dialog = builder
                .setMessage(message)
                .setNegativeButton(R.string.cancel, null)
                .setNeutralButton(getString(R.string.overdraft), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.clickedOverdraft(itemPosition);
                    }
                })
                .create();
        return dialog;
    }
}
