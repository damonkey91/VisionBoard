package com.example.mrx.visionboardapp.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.mrx.visionboardapp.Interfaces.IShowTaskInterface;
import com.example.mrx.visionboardapp.Objects.TaskItem;
import com.example.mrx.visionboardapp.R;

public class ShowTaskDialog extends DialogFragment {

    private IShowTaskInterface callback;
    private TaskItem task;
    private View view;

    public static ShowTaskDialog newInstance(TaskItem task, IShowTaskInterface callback){
        ShowTaskDialog showTaskDialog = new ShowTaskDialog();
        showTaskDialog.callback = callback;
        showTaskDialog.task = task;
        return showTaskDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        setupView();
        Dialog dialog = builder.setView(view)
                .setTitle(task.getTitle())
                .setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.editActiveTask();
                    }
                })
                .setNeutralButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.deleteActiveTask();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }

    private void setupView(){
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_show_task, null, false);
        ((TextView) view.findViewById(R.id.points_input_tv)).setText("" + task.getValue());
        ((TextView) view.findViewById(R.id.description_input_tv)).setText(task.getDescription());
    }
}
