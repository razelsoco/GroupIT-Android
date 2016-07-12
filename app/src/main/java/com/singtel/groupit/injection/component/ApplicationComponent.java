package com.singtel.groupit.injection.component;

import android.app.Application;

import com.singtel.groupit.model.DataManager;
import com.singtel.groupit.injection.module.ApplicationModule;
import com.singtel.groupit.util.GroupITSharedPreferences;
import com.singtel.groupit.view.activity.MainActivity;
import com.singtel.groupit.viewmodel.LoginViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(LoginViewModel viewModel);

    Application application();
    GroupITSharedPreferences sharedPreferences();
    DataManager dataManager();
}