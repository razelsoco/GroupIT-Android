package com.singtel.groupit.view.activity;

import android.os.Bundle;

import com.singtel.groupit.R;

public class MainActivity extends BaseActivity {
    @Override
    protected int getLayoutRes() {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
