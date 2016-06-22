package com.singtel.groupit.view.fragment;

import com.singtel.groupit.R;

/**
 * Created by lanna on 6/22/16.
 *
 */

public class MainFragment extends BaseMenuFragment {

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_main;
    }

}
