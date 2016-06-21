package com.singtel.groupit.view.activity;

import android.content.Context;

import com.singtel.groupit.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by lanna on 6/17/16.
 *
 */

public class LoginActivity extends BaseActivity {


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
