package com.example.mrx.visionboardapp.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mrx.visionboardapp.R;
import com.example.mrx.visionboardapp.ViewModel.TaskAndPointsViewModel;
import com.example.mrx.visionboardapp.ViewPager.ViewPagerAdapter;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPager = findViewById(R.id.view_pager);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager.setAdapter(pagerAdapter);
    }
//Todo: when backpressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}

