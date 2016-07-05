package com.singtel.groupit.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.ActivityMainBinding;
import com.singtel.groupit.uiutil.UiUtils;
import com.singtel.groupit.view.fragment.MainFragment;
import com.singtel.groupit.view.fragment.MenuFragment;
import com.singtel.groupit.viewmodel.MainViewModel;


/**
 * Created by lanna on 6/17/16.
 *
 */

public class MainActivity extends SlidingActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected int getLayoutRes() {
        return 0; // no need call setContentView(..) in BaseActivity
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new MainViewModel(this);
        binding.setViewModel(viewModel);
        setSupportActionBar(binding.actionBarMain.toolbar);

        UiUtils.replaceFragment(this, MainFragment.newInstance(), R.id.fragment_content, false);
    }

    @Override
    protected void onDrawerClosed() {
        UiUtils.hideKeyboard(this, findViewById(R.id.menu_frame));
    }

    @Override
    protected Fragment onCreateMenuPanel() {
        return MenuFragment.getInstance();
    }

    @Override
    public void onBackPressed() {
        if (viewModel.onBackPressed()) {
            return;
        }

        super.onBackPressed();
    }

}
