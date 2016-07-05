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

public class DashboardFragment extends BaseMenuFragment {
    public static DashboardFragment getInstance() {
        return new DashboardFragment();
    }
    FragmentMenuBinding binding;
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_menu;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(binding == null) {
            binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
            binding.setModel(new DashBoardViewModel(this));
        }
        return binding.getRoot();
    }

}
