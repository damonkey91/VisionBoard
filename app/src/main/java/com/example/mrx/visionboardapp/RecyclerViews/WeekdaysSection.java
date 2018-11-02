package com.example.mrx.visionboardapp.RecyclerViews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mrx.visionboardapp.Objects.Task;
import com.example.mrx.visionboardapp.R;

import java.util.ArrayList;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class WeekdaysSection extends StatelessSection {

    private String title;
    private ArrayList<Task> taskList;
    public WeekdaysSection(String title, ArrayList<Task> taskList) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.item_recycler_view)
                .headerResourceId(R.layout.header_recycler_view)
                .build());

        this.title = title;
        this.taskList = taskList;
    }

    @Override
    public int getContentItemsTotal() {
        return taskList.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        Task task = taskList.get(position);
        itemViewHolder.itemTitle.setText(task.getTitle());
        itemViewHolder.itemValue.setText(""+task.getValue());
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        headerViewHolder.headerTitle.setText(title);
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView headerTitle;

        public HeaderViewHolder(View view){
            super(view);
            headerTitle = view.findViewById(R.id.title_header);
        }

    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView itemTitle;
        private final TextView itemValue;
        private final Button finishedButton;

        public ItemViewHolder(View view) {
            super(view);
            itemTitle = view.findViewById(R.id.title_item);
            itemValue = view.findViewById(R.id.value_item);
            finishedButton = view.findViewById(R.id.button_finished_item);
        }
    }
}
