package com.example.mrx.visionboardapp.Dialogs;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.mrx.visionboardapp.Interfaces.ICreateRewardDialogInterface;
import com.example.mrx.visionboardapp.Objects.Reward;
import com.example.mrx.visionboardapp.R;
import com.example.mrx.visionboardapp.ViewModel.TaskAndPointsViewModel;

public class CreateRewardDialog extends DialogFragment {

    private ICreateRewardDialogInterface callback;
    private View view;
    private EditText rewardNameET;
    private EditText rewardPriceET;
    private String titleText;
    private String positiveButton;

    public static CreateRewardDialog newInstance(ICreateRewardDialogInterface callback){
        CreateRewardDialog dialogFragment = new CreateRewardDialog();
        dialogFragment.callback = callback;
        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        setupView();
        isActiveReward(view);
        rewardNameET.requestFocus();
        Dialog dialog = builder.setView(view)
                .setTitle(titleText)
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String rewardName = rewardNameET.getText().toString();
                        String rewardPrice = rewardPriceET.getText().toString();
                        int rewardPriceInt = rewardPrice.isEmpty() ? 0 : Integer.parseInt(rewardPrice);
                        callback.createReward(new Reward(rewardPriceInt, rewardName));
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }

    private void setupView() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_create_reward, null, false);
        rewardNameET = view.findViewById(R.id.create_reward_edittext);
        rewardPriceET = view.findViewById(R.id.create_reward_price_edittext);
        titleText = getString(R.string.create_reward);
        positiveButton = getString(R.string.create);
    }

    private void isActiveReward(View view) {
        TaskAndPointsViewModel viewModel = ViewModelProviders.of(getActivity()).get(TaskAndPointsViewModel.class);
        if (viewModel.getActiveRewardPosition() != -1) {
            Reward reward = viewModel.getRewardList().get(viewModel.getActiveRewardPosition());
            titleText = getString(R.string.edit_reward);
            positiveButton = getString(R.string.save);
            rewardNameET.setText(reward.getRewardName());
            rewardPriceET.setText(""+reward.getRewardPrice());
        }
    }
}
