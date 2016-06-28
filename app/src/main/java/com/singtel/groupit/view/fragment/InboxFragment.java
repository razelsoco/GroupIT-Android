package com.singtel.groupit.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.singtel.groupit.R;

/**
 * Created by lanna on 6/28/16.
 *
 */

public class InboxFragment extends BaseFragment {

    public static Fragment newInstance() {
        return new InboxFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.swipe_refresh_recycler_view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
