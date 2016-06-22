package com.singtel.groupit.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.singtel.groupit.R;
import com.singtel.groupit.model.Article;
import com.singtel.groupit.view.adapter.HomeArticlesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by lanna on 6/22/16.
 *
 */

public class MainFragment extends BaseMenuFragment {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private HomeArticlesAdapter adapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new HomeArticlesAdapter();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            articles.add(new Article("hey " + i));
        }
        adapter.setItemsAndNotify(articles);
    }
}
