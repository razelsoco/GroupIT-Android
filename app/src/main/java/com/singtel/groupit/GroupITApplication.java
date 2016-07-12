package com.singtel.groupit;

import android.app.Application;
import android.content.Context;

import com.singtel.groupit.injection.component.ApplicationComponent;
import com.singtel.groupit.injection.component.DaggerApplicationComponent;
import com.singtel.groupit.injection.module.ApplicationModule;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by lanna on 6/20/16.
 *
 */

public class GroupITApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        // default font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.font_avenirltpro_roman))
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        // dagger application component
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static GroupITApplication get(Context context) {
        return (GroupITApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}
