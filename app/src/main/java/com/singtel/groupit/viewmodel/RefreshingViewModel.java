package com.singtel.groupit.viewmodel;

import android.databinding.ObservableBoolean;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by lanna on 6/29/16.
 *
 */

public abstract class RefreshingViewModel implements ViewModel, SwipeRefreshLayout.OnRefreshListener {

    private final ObservableBoolean refreshing = new ObservableBoolean(false);

    public ObservableBoolean getRefreshing() {
        return refreshing;
    }

    public void setRefreshing(boolean isRefreshing) {
        refreshing.set(isRefreshing);
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return this;
    }
}
