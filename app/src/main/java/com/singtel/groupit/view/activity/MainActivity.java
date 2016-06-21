package com.singtel.groupit.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.singtel.groupit.R;
import com.singtel.groupit.util.AlertHelper;
import com.singtel.groupit.util.NetworkUtils;
import com.singtel.groupit.util.UiUtils;
import com.singtel.groupit.view.fragment.MenuFragment;


/**
 * Created by lanna on 6/17/16.
 *
 */

public class MainActivity extends SlidingActivity {

    @Override
    protected int getLayoutRes() {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createCustomActionBar();
    }

    @Override
    protected void onDrawerClosed() {
        UiUtils.hideKeyboard(this, findViewById(R.id.menu_frame));
    }

    @Override
    protected Fragment onCreateMenuPanel() {
        return MenuFragment.getInstance();
    }

    private void createCustomActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the action bar to show a dropdown list.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

//        View customNav = LayoutInflater.from(this).inflate(R.layout.action_bar_main, null);

//        actionBar.setCustomView(customNav, new ActionBar.LayoutParams(Gravity.LEFT));
        actionBar.setDisplayShowCustomEnabled(true);

        final View ivMenu = toolbar.findViewById(R.id.iv_action_bar_menu);
        ivMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ivMenu.clearAnimation();
                if (NetworkUtils.isOnline(getApplicationContext())) {
                    showMenu();
                } else {
                    AlertHelper.showInternetAlert(getApplicationContext(), null);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isMenuShowing()) {
            showContent();
            return;
        }

        super.onBackPressed();
    }
}
