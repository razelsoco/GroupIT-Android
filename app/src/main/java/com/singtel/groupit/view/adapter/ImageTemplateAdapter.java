package com.singtel.groupit.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.ItemTemplateBinding;
import com.singtel.groupit.model.domain.Template;
import com.singtel.groupit.viewmodel.ItemTemplateViewModel;
import com.singtel.groupit.viewmodel.ViewModel;

/**
 * Created by razelsoco on 6/28/16.
 *
 */

public class ImageTemplateAdapter extends BaseRecyclerAdapter<Template, ImageTemplateAdapter.BindingHolder> {

    private Context context;
    protected OnViewModelChangeListener onItemClickListener;

    public interface OnViewModelChangeListener {
        void onItemClicked(View view, int position, ViewModel data);
    }

    public ImageTemplateAdapter(Context context, OnViewModelChangeListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTemplateBinding itemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_template, parent, false);

        return new BindingHolder(itemBinding, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        ItemTemplateBinding itemBinding = holder.binding;

        ItemTemplateViewModel itemTemplateViewModel = new ItemTemplateViewModel(getItem(position));
        itemBinding.setViewModel(itemTemplateViewModel);

        if(position == 0)
            onItemClickListener.onItemClicked(null, 0, itemTemplateViewModel);

    }

    /*
        View Holder
     */

    public static class BindingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ItemTemplateBinding binding;
        private OnViewModelChangeListener onItemClickListener;
        public BindingHolder(ItemTemplateBinding binding, OnViewModelChangeListener onItemClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onItemClickListener = onItemClickListener;
            this.binding.templateImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(this.binding.getViewModel().selectedState.get()) return;

            this.binding.getViewModel().onImageClicked(v);
            if(onItemClickListener != null)
                onItemClickListener.onItemClicked(v, getAdapterPosition(), this.binding.getViewModel());

        }
    }

}
