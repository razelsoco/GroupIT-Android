package com.singtel.groupit.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.singtel.groupit.GroupITApplication;
import com.singtel.groupit.R;

public class SplashActivity extends BaseActivity
    implements Handler.Callback {

    private Handler handler;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(0);
                }
        }).start();
    }

    @Override
    public boolean handleMessage(Message msg) {
        String token = GroupITApplication.get(this).getComponent().sharedPreferences().getUserToken(this);
        if (TextUtils.isEmpty(token)) {
            gotoLoginScreen();
        } else {
            gotoMainScreen();
        }
        return true;
    }

    private void gotoLoginScreen() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void gotoMainScreen() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
