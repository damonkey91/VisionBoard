package com.example.mrx.visionboardapp.Objects;

public class Reward {

    private int rewardPrice;
    private String rewardName;

    public Reward(int rewardPrice, String rewardName){
        this.rewardPrice = rewardPrice;
        this.rewardName = rewardName;
    }

    public int getRewardPrice() {
        return rewardPrice;
    }

    public String getRewardName() {
        return rewardName;
    }
}
