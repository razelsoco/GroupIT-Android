package com.singtel.groupit;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by lanna on 6/20/16.
 *
 */

public class GroupITApplication extends Application {

    private static GroupITApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // default font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.font_avenirltpro_roman))
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public static GroupITApplication getInstance() {
        return instance;
    }
}
