package com.singtel.groupit.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.ItemHomeArticleBinding;
import com.singtel.groupit.model.Article;
import com.singtel.groupit.viewmodel.ItemArticleViewModel;

/**
 * Created by lanna on 6/22/16.
 *
 */

public class HomeArticlesAdapter extends BaseRecyclerAdapter<Article, HomeArticlesAdapter.BindingHolder> {

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemHomeArticleBinding itemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_home_article, parent, false);

        return new BindingHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        ItemHomeArticleBinding itemBinding = holder.binding;
        itemBinding.setViewModel(new ItemArticleViewModel(getItem(position)));
    }

    /*
        View Holder
     */

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ItemHomeArticleBinding binding;

        public BindingHolder(ItemHomeArticleBinding binding) {
            super(binding.rootView);
            this.binding = binding;
        }
    }
}
