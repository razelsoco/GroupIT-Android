package com.singtel.groupit.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.ItemSelectableContactBinding;
import com.singtel.groupit.model.domain.User;
import com.singtel.groupit.viewmodel.ItemSelectableContactViewModel;

/**
 * Created by razelsoco on 6/28/16.
 *
 */

public class SelectUsersAdapter extends BaseRecyclerAdapter<User, SelectUsersAdapter.BindingHolder> {

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSelectableContactBinding itemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_selectable_contact, parent, false);

        return new BindingHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        ItemSelectableContactBinding itemBinding = holder.binding;
        itemBinding.setViewModel(new ItemSelectableContactViewModel(getItem(position)));
    }

    /*
        View Holder
     */

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ItemSelectableContactBinding binding;

        public BindingHolder(ItemSelectableContactBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
