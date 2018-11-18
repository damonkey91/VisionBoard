package com.example.mrx.visionboardapp.RecyclerViews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mrx.visionboardapp.Interfaces.IRewardRecyclerViewInterface;
import com.example.mrx.visionboardapp.Objects.Reward;
import com.example.mrx.visionboardapp.R;

import java.util.ArrayList;

public class RewardRecyclerViewAdapter extends RecyclerView.Adapter<RewardRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Reward> rewardList;
    private IRewardRecyclerViewInterface callback;

    public RewardRecyclerViewAdapter(ArrayList<Reward> rewardList, IRewardRecyclerViewInterface callback) {
        this.rewardList = rewardList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_rewards, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reward reward = rewardList.get(position);
        holder.rewardName.setText(reward.getRewardName());
        holder.rewardPrice.setText(""+reward.getRewardPrice()+"$");
    }

    @Override
    public int getItemCount() {
        return rewardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView rewardName;
        private TextView rewardPrice;
        private Button buyButton;

        public ViewHolder(View itemView) {
            super(itemView);
            rewardName = itemView.findViewById(R.id.reward_title_item);
            rewardPrice = itemView.findViewById(R.id.reward_price_item);
            buyButton = itemView.findViewById(R.id.button_buy_item);
            buyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.clickedBuy(getAdapterPosition());
                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            callback.clickedOnItem(getAdapterPosition());
        }
    }
}
