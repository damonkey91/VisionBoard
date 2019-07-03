package com.example.mrx.visionboardapp.ViewPager;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

import com.example.mrx.visionboardapp.Interfaces.IOnPageChanged;

public class PageChangeListener implements ViewPager.OnPageChangeListener {
    private IOnPageChanged callback;

    public PageChangeListener(@NonNull IOnPageChanged callback) {
        this.callback = callback;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
        callback.changedPage(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }
}
