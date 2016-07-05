package com.singtel.groupit.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.singtel.groupit.model.DataManager;
import com.singtel.groupit.GroupITApplication;
import com.singtel.groupit.R;
import com.singtel.groupit.model.ArticlesResponse;
import com.singtel.groupit.uiutil.AlertUtils;
import com.singtel.groupit.uiutil.DividerItemDecoration;
import com.singtel.groupit.util.LogUtils;
import com.singtel.groupit.util.NetworkUtils;
import com.singtel.groupit.view.adapter.HomeArticlesAdapter;

import java.io.IOException;

import butterknife.Bind;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lanna on 6/22/16.
 *
 */

public class MainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.swipe_refresh_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private HomeArticlesAdapter adapter;

    private DataManager dataManager;
    private CompositeSubscription mSubscriptions;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new HomeArticlesAdapter(getContext());

        dataManager = GroupITApplication.get(getContext()).getComponent().dataManager();
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.swipe_refresh_recycler_view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), R.drawable.divider_vertical_dark));
        recyclerView.setAdapter(adapter);

        loadStoriesIfNetworkConnected();
    }

    @Override
    public void onRefresh() {
        loadStoriesIfNetworkConnected();
    }

    private void loadStoriesIfNetworkConnected() {
        if (NetworkUtils.isOnline(getActivity())) {
            swipeRefreshLayout.setRefreshing(true);
            fetchTopStories();
        }
        else { // no internet connection
            swipeRefreshLayout.setRefreshing(false);
            AlertUtils.showInternetAlert(getContext(), null);
        }
    }

    private void fetchTopStories() {
        //LogUtils.w(MainFragment.this, "fetchTopStories: ");
        mSubscriptions.add(dataManager.getTopStories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(dataManager.getScheduler())
                .subscribe(new Subscriber<ArticlesResponse>() {
                    @Override
                    public void onCompleted() {
                        //LogUtils.w(MainFragment.this, "fetchTopStories: onCompleted");
                        swipeRefreshLayout.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        swipeRefreshLayout.setRefreshing(false);
                        if (e instanceof HttpException) {
                            // We had non-2XX http error
                            LogUtils.w(MainFragment.this, "fetchTopStories: onError: "+((HttpException) e).response().code());
                        }
                        else if (e instanceof IOException) {
                            // A network or conversion error happened
                            LogUtils.w(MainFragment.this, "fetchTopStories: onError: A network or conversion error happened");
                        }

                        // We don't know what happened. We need to simply convert to an unknown error
                        LogUtils.w(MainFragment.this, "fetchTopStories: onError: "+ e.getMessage());
                    }

                    @Override
                    public void onNext(ArticlesResponse articles) {
                        //LogUtils.w(MainFragment.this, "fetchTopStories: onNext: "+", "+ articles.articles);
                        adapter.setItems(articles.articles);
                    }
                })
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
}
