package com.example.mrx.visionboardapp.activities;

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
import android.view.View;
import android.view.ViewGroup;

import com.example.mrx.visionboardapp.R;
import com.example.mrx.visionboardapp.RecyclerViews.RewardRecyclerViewAdapter;
import com.example.mrx.visionboardapp.ViewModel.TaskAndPointsViewModel;

public class RewardsAndPointsFragment extends Fragment {

    private TaskAndPointsViewModel viewModel;
    private View view;
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
        setupListView();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.points_menu, menu);
        menu.findItem(R.id.actionbar_points).setTitle(""+viewModel.getTotalPoints()+"$");
    }

    private void setupListView(){
        adapter = new RewardRecyclerViewAdapter();
        listView = view.findViewById(R.id.reward_list_view);
        listView.setHasFixedSize(true);
        listView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(adapter);
    }


}
