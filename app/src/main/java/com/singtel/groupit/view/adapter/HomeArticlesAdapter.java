package com.singtel.groupit.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.ItemHomeArticleBinding;
import com.singtel.groupit.model.domain.ArticleModel;
import com.singtel.groupit.viewmodel.ArticleViewModel;

/**
 * Created by lanna on 6/22/16.
 *
 */

public class HomeArticlesAdapter extends BaseRecyclerAdapter<ArticleModel, HomeArticlesAdapter.BindingHolder> {

    private Context context;

    public HomeArticlesAdapter(Context context) {
        this.context = context;
    }

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
        itemBinding.setViewModel(new ArticleViewModel(context, getItem(position)));
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
