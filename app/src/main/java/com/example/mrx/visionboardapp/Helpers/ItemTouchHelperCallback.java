package com.example.mrx.visionboardapp.Helpers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.example.mrx.visionboardapp.Interfaces.IItemMovedCallback;
import com.example.mrx.visionboardapp.Interfaces.IItemTouchHelperAdapter;

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private IItemTouchHelperAdapter adapterCallback;
    private IItemMovedCallback iItemMovedCallback;

    public ItemTouchHelperCallback(IItemTouchHelperAdapter adapterCallback, IItemMovedCallback iItemMovedCallback) {
        this.adapterCallback = adapterCallback;
        this.iItemMovedCallback = iItemMovedCallback;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        adapterCallback.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        iItemMovedCallback.itemMovedCallback();
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }
}
