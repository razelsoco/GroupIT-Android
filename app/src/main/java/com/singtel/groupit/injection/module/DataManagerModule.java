package com.singtel.groupit.injection.module;

import com.singtel.groupit.data.remote.GroupITService;
import com.singtel.groupit.data.remote.RetrofitHelper;
import com.singtel.groupit.injection.scope.PerDataManager;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Provide dependencies to the DataManager, mainly Helper classes and Retrofit services.
 */
@Module
public class DataManagerModule {

    public DataManagerModule() {

    }

    @Provides
    @PerDataManager
    GroupITService provideGroupITService() {
        return new RetrofitHelper().newGroupITService();
    }

    @Provides
    @PerDataManager
    Scheduler provideSubscribeScheduler() {
        return Schedulers.io();
    }
}