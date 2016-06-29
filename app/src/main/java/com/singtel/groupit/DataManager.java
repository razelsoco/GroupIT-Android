package com.singtel.groupit;

import android.content.Context;

import com.singtel.groupit.injection.component.DaggerDataManagerComponent;
import com.singtel.groupit.injection.module.DataManagerModule;
import com.singtel.groupit.model.ArticlesResponse;
import com.singtel.groupit.model.NotesResponse;
import com.singtel.groupit.model.remote.GroupITService;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;

public class DataManager {

    @Inject GroupITService mGroupITService;
    @Inject Scheduler mSubscribeScheduler;

    public DataManager(Context context) {
        injectDependencies(context);
    }

    /* This constructor is provided so we can set up a DataManager with mocks from unit test.
     * At the moment this is not possible to do with Dagger because the Gradle APT plugin doesn't
     * work for the unit test variant, plus Dagger 2 doesn't provide a nice way of overriding
     * modules */
    public DataManager(GroupITService groupITService, Scheduler subscribeScheduler) {
        mGroupITService = groupITService;
        mSubscribeScheduler = subscribeScheduler;
    }

    private void injectDependencies(Context context) {
        DaggerDataManagerComponent.builder()
                .applicationComponent(GroupITApplication.get(context).getComponent())
                .dataManagerModule(new DataManagerModule())
                .build()
                .inject(this);
    }

    public Scheduler getScheduler() {
        return mSubscribeScheduler;
    }

    public Observable<ArticlesResponse> getTopStories() {
        return mGroupITService.getTopStories();
    }

    public Observable<NotesResponse> getInbox() {
        return mGroupITService.getInbox();
    }

    public Observable<NotesResponse> getSentNotes() {
        return mGroupITService.getSentNotes();
    }
}
