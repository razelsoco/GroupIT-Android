package com.singtel.groupit.injection.component;

import android.app.Application;

import com.singtel.groupit.model.DataManager;
import com.singtel.groupit.injection.module.ApplicationModule;
import com.singtel.groupit.view.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);

    Application application();
    DataManager dataManager();
}