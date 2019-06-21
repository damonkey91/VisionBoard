package com.example.mrx.visionboardapp.RecyclerViews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mrx.visionboardapp.Interfaces.IItemTouchHelperAdapter;
import com.example.mrx.visionboardapp.Interfaces.IWeekdaysSectionInterface;
import com.example.mrx.visionboardapp.Objects.HeaderItem;
import com.example.mrx.visionboardapp.Objects.RecyclerViewItem;
import com.example.mrx.visionboardapp.Objects.TaskItem;
import com.example.mrx.visionboardapp.R;

import java.util.ArrayList;
import java.util.Collections;

public class MySectionRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IItemTouchHelperAdapter {
    public static final int HEADER_TYPE = 1;
    public static final int ITEM_TYPE = 2;

    private ArrayList<RecyclerViewItem> list;
    private IWeekdaysSectionInterface callback;

    public MySectionRecyclerViewAdapter(ArrayList<RecyclerViewItem> list, IWeekdaysSectionInterface callback) {
        this.list = list;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case HEADER_TYPE:
                return new HeaderViewHolder(layoutInflater.inflate(R.layout.header_recycler_view, parent, false));
            case ITEM_TYPE:
                return new ItemViewHolder(layoutInflater.inflate(R.layout.item_recycler_view, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case HEADER_TYPE:
                bindHeaderViewHolder(holder, position);
                break;
            case ITEM_TYPE:
                bindItemViewHolder(holder, position);
                break;
        }
    }

    private void bindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        HeaderItem headerItem = (HeaderItem) list.get(position);
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        headerViewHolder.headerTitle.setText(headerItem.getTitle());
    }

    private void bindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        TaskItem taskItem = (TaskItem) list.get(position);
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.itemTitle.setText(taskItem.getTitle());
        itemViewHolder.itemValue.setText("" + taskItem.getValue());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public void onItemMove(int fromPos, int toPos) {
        if (list.get(fromPos).getType() == ITEM_TYPE) {
            if (fromPos < toPos) {
                for (int i = fromPos; i < toPos; i++) {
                    Collections.swap(list, i, i + 1);
                }
            } else {
                for (int i = fromPos; i > toPos; i--) {
                    Collections.swap(list, i, i - 1);
                }
            }
            notifyItemMoved(fromPos, toPos);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        private final TextView headerTitle;

        public HeaderViewHolder(View view){
            super(view);
            headerTitle = view.findViewById(R.id.title_header);
            Button addButton = view.findViewById(R.id.add_task_button);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int taskPosition = getAdapterPosition() + 1;
                    callback.clickedCreateTask(taskPosition);
                }
            });
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView itemTitle;
        private final TextView itemValue;
        private final Button finishedButton;

        public ItemViewHolder(View view) {
            super(view);
            itemTitle = view.findViewById(R.id.title_item);
            itemValue = view.findViewById(R.id.value_item);
            finishedButton = view.findViewById(R.id.button_finished_item);
            finishedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.clickedFinishedTask(getAdapterPosition(), Integer.parseInt(itemValue.getText().toString()));
                }
            });
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            callback.clickedOnItem(getAdapterPosition());
        }
    }
}
