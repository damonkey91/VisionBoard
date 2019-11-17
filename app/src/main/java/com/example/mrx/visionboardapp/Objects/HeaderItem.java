package com.example.mrx.visionboardapp.Objects;

import com.example.mrx.visionboardapp.RecyclerViews.MySectionRecyclerViewAdapter;

public class HeaderItem extends RecyclerViewItem {
    private String title;
    private WeekdaysEnum day;

    public HeaderItem(String header, WeekdaysEnum day) {
        super(MySectionRecyclerViewAdapter.HEADER_TYPE);
        this.title = header;
        this.day = day;
    }

    public String getTitle() {
        return title;
    }
    public WeekdaysEnum getDay(){
        return day;
    }
}
