package com.example.mrx.visionboardapp.Objects;

import com.example.mrx.visionboardapp.RecyclerViews.MySectionRecyclerViewAdapter;

public class HeaderItem extends RecyclerViewItem {
    private String title;

    public HeaderItem(String header) {
        super(MySectionRecyclerViewAdapter.HEADER_TYPE);
        this.title = header;
    }

    public String getTitle() {
        return title;
    }
}
