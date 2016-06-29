package com.singtel.groupit.viewmodel;

import android.view.View;

import com.singtel.groupit.uiutil.AlertUtils;
import com.singtel.groupit.util.NetworkUtils;
import com.singtel.groupit.view.activity.SlidingActivity;

import org.jetbrains.annotations.NotNull;

/**
 * Created by lanna on 6/27/16.
 *
 */

public class MainViewModel implements ViewModel {

    private SlidingActivity context;

    public MainViewModel(@NotNull SlidingActivity context) {
        this.context = context;
    }

    public void onClickMenu(View view) {
        view.clearAnimation();
        if (NetworkUtils.isOnline(context)) {
            context.showMenu();
        } else {
            AlertUtils.showInternetAlert(context, null);
        }
    }

    public boolean onBackPressed() {
        if (context.isMenuShowing()) {
            context.showContent();
            return true;
        }

        return false;
    }

    @Override
    public void destroy() {

    }
}

