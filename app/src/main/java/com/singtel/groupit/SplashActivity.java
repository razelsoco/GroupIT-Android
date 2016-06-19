package com.singtel.groupit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SplashActivity extends BaseActivity
    implements Handler.Callback
{

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new Handler(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(0);
                }
        }).start();
    }

    @Override
    public boolean handleMessage(Message msg) {
        gotoLoginScreen();
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
