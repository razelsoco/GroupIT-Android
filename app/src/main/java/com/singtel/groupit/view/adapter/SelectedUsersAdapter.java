package com.singtel.groupit.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.ItemRecepientBinding;
import com.singtel.groupit.model.domain.User;
import com.singtel.groupit.viewmodel.ItemRecepientViewModel;

/**
 * Created by razelsoco on 6/28/16.
 *
 */

public class SelectedUsersAdapter extends BaseRecyclerAdapter<User, SelectedUsersAdapter.BindingHolder> {

    private Context context;

    public SelectedUsersAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRecepientBinding itemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_recepient, parent, false);

        return new BindingHolder(itemBinding, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        ItemRecepientBinding itemBinding = holder.binding;
        itemBinding.setViewModel(new ItemRecepientViewModel(getItem(position)));
    }

    /*
        View Holder
     */

    public static class BindingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ItemRecepientBinding binding;
        private OnItemClickListener<User> onItemClickListener;
        public BindingHolder(ItemRecepientBinding binding, OnItemClickListener<User> onItemClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onItemClickListener = onItemClickListener;
            this.binding.btDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListener != null) {
                onItemClickListener.onItemClicked(v, getAdapterPosition(), this.binding.getViewModel().user);
                this.binding.getViewModel().user.setSelected(false);
            }
        }
    }
}
