package com.example.mrx.visionboardapp.activities;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.mrx.visionboardapp.Interfaces.IOnPageChanged;
import com.example.mrx.visionboardapp.R;
import com.example.mrx.visionboardapp.ViewPager.PageChangeListener;
import com.example.mrx.visionboardapp.ViewPager.ViewPagerAdapter;

public class ViewPagerActivity extends AppCompatActivity implements View.OnLongClickListener, IOnPageChanged {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPager = findViewById(R.id.view_pager);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new PageChangeListener(this));
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        clickableToolbarTitle(toolbar);
        setToolbarTitle();
    }
//Todo: when backpressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void clickableToolbarTitle(Toolbar toolbar){
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setOnLongClickListener(this);
    }

    private void setToolbarTitle(){
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        int page = viewPager.getCurrentItem();
        String title = pagerAdapter.getPageTitle(page).toString();
        toolbarTitle.setText(title);
    }

    @Override
    public boolean onLongClick(View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void changedPage(int page) {
        setToolbarTitle();
    }
}

