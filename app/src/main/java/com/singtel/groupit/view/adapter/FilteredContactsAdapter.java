package com.singtel.groupit.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.ItemContactBinding;
import com.singtel.groupit.model.domain.Contact;
import com.singtel.groupit.viewmodel.ItemRecepientViewModel;

/**
 * Created by razelsoco on 6/28/16.
 *
 */

public class FilteredContactsAdapter extends BaseRecyclerAdapter<Contact, FilteredContactsAdapter.BindingHolder> {

    private Context context;

    public FilteredContactsAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemContactBinding itemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_contact, parent, false);

        return new BindingHolder(itemBinding, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        ItemContactBinding itemBinding = holder.binding;
        itemBinding.setViewModel(new ItemRecepientViewModel(getItem(position)));
    }

    /*
        View Holder
     */

    public static class BindingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ItemContactBinding binding;
        private OnItemClickListener<Contact> onItemClickListener;

        public BindingHolder(ItemContactBinding binding, OnItemClickListener<Contact> onItemClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onItemClickListener = onItemClickListener;
            this.binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListener != null){
                onItemClickListener.onItemClicked(v, getAdapterPosition(), binding.getViewModel().contact);
                binding.getViewModel().contact.setSelected(true);
            }

        }
    }
}
