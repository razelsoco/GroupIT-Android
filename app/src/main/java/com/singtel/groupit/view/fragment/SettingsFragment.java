package com.singtel.groupit.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.FragmentSettingsBinding;

/**
 * Created by razelsoco on 6/23/16.
 *
 */

public class SettingsFragment extends BaseMenuFragment {
    public static SettingsFragment getInstance() {
        return new SettingsFragment();
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_settings;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSettingsBinding binding = DataBindingUtil.inflate(inflater,getLayoutRes(),container, false);
        binding.setFragment(this);
        return binding.getRoot();
    }

    public void onBackClick(View view){
        onBackPressed();
    }

    public void onAboutClick(View view){

    }

    public void onFeedbackClick(View view){

    }

    public void onTNCClick(View view){

    }

    public void onSignoutClick(View view){

    }
}
