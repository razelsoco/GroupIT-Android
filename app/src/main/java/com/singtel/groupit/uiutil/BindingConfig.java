package com.singtel.groupit.uiutil;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.adapters.ListenerUtil;
import android.support.v4.widget.SwipeRefreshLayout;

import com.singtel.groupit.R;

/**
 * Created by lanna on 6/29/16.
 * Copy Right: Sai
 * from https://github.com/saiwu-bigkoo/Android-MVVMFramework
 */

public class BindingConfig {

    @InverseBindingAdapter(attribute = "refreshing", event = "refreshingAttrChanged")
    public static boolean isRefreshing(SwipeRefreshLayout view) {
        return view.isRefreshing();
    }

    @BindingAdapter("refreshing")
    public static void setRefreshing(final SwipeRefreshLayout view, final boolean refreshing) {
        if (refreshing != view.isRefreshing()) {
            UiUtils.doWhenViewHasSize(view, new Runnable() {
                @Override
                public void run() {
                    view.setRefreshing(refreshing);
                }
            });
        }
    }

    @BindingAdapter(value = {"onRefreshListener", "refreshingAttrChanged"}, requireAll = false)
    public static void setOnRefreshListener(final SwipeRefreshLayout view,
                                            final SwipeRefreshLayout.OnRefreshListener listener,
                                            final InverseBindingListener refreshingAttrChanged) {

        SwipeRefreshLayout.OnRefreshListener newValue = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (listener != null) {
                    if (refreshingAttrChanged != null) {
                        refreshingAttrChanged.onChange();
                    }
                    listener.onRefresh();
                }
            }
        };

        SwipeRefreshLayout.OnRefreshListener oldValue = ListenerUtil.trackListener(view, newValue, R.id.onRefreshListener);
        if (oldValue != null) {
            view.setOnRefreshListener(null);
        }
        view.setOnRefreshListener(newValue);
    }

}

