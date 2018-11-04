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
import android.widget.EditText;

import com.example.mrx.visionboardapp.Interfaces.ICreateRewardDialogInterface;
import com.example.mrx.visionboardapp.Objects.Reward;
import com.example.mrx.visionboardapp.R;

public class CreateRewardDialog extends DialogFragment {

    private ICreateRewardDialogInterface callback;

    public static CreateRewardDialog newInstance(ICreateRewardDialogInterface callback){
        CreateRewardDialog dialogFragment = new CreateRewardDialog();
        dialogFragment.callback = callback;
        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_create_reward, null, false);
        Dialog dialog = builder.setView(view)
                .setTitle(R.string.create_reward)
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String rewardName = ((EditText) view.findViewById(R.id.create_reward_edittext)).getText().toString();
                        String rewardPrice = ((EditText) view.findViewById(R.id.create_reward_price_edittext)).getText().toString();
                        int rewardPriceInt = rewardPrice.isEmpty() ? 0 : Integer.parseInt(rewardPrice);
                        callback.createReward(new Reward(rewardPriceInt, rewardName));
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
