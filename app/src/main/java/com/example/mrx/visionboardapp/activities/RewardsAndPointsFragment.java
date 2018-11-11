package com.example.mrx.visionboardapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mrx.visionboardapp.Dialogs.AlertingDialog;
import com.example.mrx.visionboardapp.Dialogs.CreateRewardDialog;
import com.example.mrx.visionboardapp.Helpers.HandleSharedPreferences;
import com.example.mrx.visionboardapp.Interfaces.ICreateRewardDialogInterface;
import com.example.mrx.visionboardapp.Interfaces.IRewardRecyclerViewInterface;
import com.example.mrx.visionboardapp.Objects.Reward;
import com.example.mrx.visionboardapp.R;
import com.example.mrx.visionboardapp.RecyclerViews.RewardRecyclerViewAdapter;
import com.example.mrx.visionboardapp.ViewModel.TaskAndPointsViewModel;

public class RewardsAndPointsFragment extends Fragment implements IRewardRecyclerViewInterface, ICreateRewardDialogInterface, Observer<Integer> {

    private TaskAndPointsViewModel viewModel;
    private View view;
    private TextView pointsTextView;
    private MenuItem pointsMenuItem;
    private RecyclerView listView;
    private RewardRecyclerViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(getActivity()).get(TaskAndPointsViewModel.class);
        view = inflater.inflate(R.layout.fragment_rewards_and_points, container, false);
        pointsTextView = view.findViewById(R.id.pointTextView);
        setupListView();
        setupAddButton();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.points_menu, menu);
        pointsMenuItem = menu.findItem(R.id.actionbar_points);
        viewModel.getTotalPoints().observe(this, this);
        pointsMenuItem.setTitle(viewModel.getTotalPoints().getValue()+"$");
    }

    private void setupListView(){
        adapter = new RewardRecyclerViewAdapter(viewModel.getRewardList(),this);
        listView = view.findViewById(R.id.reward_list_view);
        listView.setHasFixedSize(true);
        listView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(adapter);
    }

    @Override
    public void clickedBuy(int position) {
        if (viewModel.gotEnoughPoints(position)){
            viewModel.subtractPointsAndRemoveReward(position);
            adapter.notifyItemRemoved(position);
        } else{
            AlertingDialog.newInstance(getString(R.string.not_enough_points)).show(getFragmentManager(), "hh");
        }

    }

    private void setupAddButton(){
        final ICreateRewardDialogInterface callback = this;
        view.findViewById(R.id.add_reward_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateRewardDialog.newInstance(callback).show(getFragmentManager(), "hh");
            }
        });
    }

    @Override
    public void createReward(Reward reward) {
        viewModel.addToRewardList(reward);
        adapter.notifyItemInserted(0);
    }

    @Override
    public void onChanged(@Nullable Integer integer) {
        pointsTextView.setText(integer + "$");
        pointsMenuItem.setTitle(integer + "$");
    }
}

