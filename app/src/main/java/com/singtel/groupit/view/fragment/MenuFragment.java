package com.singtel.groupit.view.fragment;

import com.singtel.groupit.R;

/**
 * Created by lanna on 6/20/16.
 *
 */

public class MenuFragment extends BaseMenuFragment {

    public static MenuFragment getInstance() {
        return new MenuFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_menu;
    }
}
