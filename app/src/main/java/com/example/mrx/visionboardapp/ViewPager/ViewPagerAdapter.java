package com.example.mrx.visionboardapp.ViewPager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mrx.visionboardapp.R;
import com.example.mrx.visionboardapp.activities.RewardsAndPointsFragment;
import com.example.mrx.visionboardapp.activities.VisionBoardFragment;
import com.example.mrx.visionboardapp.activities.WeekdayTasksFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 3;

    private Context context;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new VisionBoardFragment();
            case 1:
                return new WeekdayTasksFragment();
            case 2:
                return new RewardsAndPointsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getString(R.string.vision_board);
            case 1:
                return context.getString(R.string.tasks);
            case 2:
                return context.getString(R.string.rewards_and_points);
            default:
                return null;
        }
    }
}
