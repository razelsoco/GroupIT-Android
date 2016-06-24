package com.singtel.groupit.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.FragmentMenuBinding;
import com.singtel.groupit.viewmodel.DashBoardViewModel;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentMenuBinding mBinding = DataBindingUtil.inflate(inflater,getLayoutRes(),container, false);
        mBinding.setModel(new DashBoardViewModel(this));
        return mBinding.getRoot();
    }

}
