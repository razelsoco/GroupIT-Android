package com.singtel.groupit.model;

import android.content.Context;

import com.singtel.groupit.GroupITApplication;
import com.singtel.groupit.injection.component.DaggerDataManagerComponent;
import com.singtel.groupit.injection.module.DataManagerModule;
import com.singtel.groupit.model.domain.AccountInfo;
import com.singtel.groupit.model.domain.User;
import com.singtel.groupit.model.remote.GroupITService;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;

public class DataManager {

    @Inject GroupITService mGroupITService;
    @Inject Scheduler mSubscribeScheduler;
    PreferencesManager prefManager;

    public DataManager(Context context) {
        injectDependencies(context);
        prefManager = PreferencesManager.getInstance();
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

    public Observable<AccountInfo> login(String username, String password, String grantType) {
        return mGroupITService.getToken(username, password, grantType);
    }

    public Observable<ArticlesResponse> getTopStories() {
        return mGroupITService.getTopStories(prefManager.getAuthToken());
    }

    public Observable<TestUserResponse> getUser() {
        return mGroupITService.getUser(prefManager.getAuthToken());
    }

    public Observable<List<User>> getUsers() {
        return mGroupITService.getUsers(prefManager.getAuthToken());
    }

    public Observable<TestTemplatesResponse> getTemplates() {
        return mGroupITService.getTemplates(prefManager.getAuthToken());
    }

    public Observable<NotesResponse> getInbox() {
        return mGroupITService.getInbox(prefManager.getAuthToken());
    }

    public Observable<NotesResponse> getSentNotes() {
        return mGroupITService.getSentNotes(prefManager.getAuthToken());
    }
}
