package com.singtel.groupit;

import android.app.Application;

/**
 * Created by lanna on 6/20/16.
 */

public class GroupITApplication extends Application {

    private static GroupITApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static GroupITApplication getInstance() {
        return instance;
    }
}
