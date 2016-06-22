package com.singtel.groupit.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by lanna on 6/1/16.
 *
 */

public abstract class BaseRecyclerAdapter<ItemModel, ViewHolder extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<ViewHolder> {

    public static interface OnItemClickListener<ItemModel> {
        public void onItemClicked(View view, int position, ItemModel data);
    }
    protected OnItemClickListener<ItemModel> onItemClickListener;

    protected List<ItemModel> mItems;

    public BaseRecyclerAdapter() {}

    public void setOnItemClickListener(OnItemClickListener<ItemModel> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<ItemModel> getItems() {
        return mItems;
    }

    public void setItems(List<ItemModel> mItemModels) {
        this.mItems = mItemModels;
    }

    public void setItemsAndNotify(List<ItemModel> items) {
        setItems(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (mItems == null) ? 0 : mItems.size();
    }

    public ItemModel getItem(int position) {
        return (mItems == null || mItems.isEmpty()) ? null : mItems.get(position);
    }
}
