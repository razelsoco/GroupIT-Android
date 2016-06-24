package com.singtel.groupit.injection.component;

import com.singtel.groupit.DataManager;
import com.singtel.groupit.injection.module.DataManagerModule;
import com.singtel.groupit.injection.scope.PerDataManager;

import dagger.Component;

@PerDataManager
@Component(dependencies = ApplicationComponent.class, modules = DataManagerModule.class)
public interface DataManagerComponent {

    void inject(DataManager dataManager);
}