package com.singtel.groupit.model;

import android.content.Context;

import com.singtel.groupit.GroupITApplication;
import com.singtel.groupit.injection.component.DaggerDataManagerComponent;
import com.singtel.groupit.injection.module.DataManagerModule;
import com.singtel.groupit.model.domain.AccountInfo;
import com.singtel.groupit.model.domain.User;
import com.singtel.groupit.model.domain.Note;
import com.singtel.groupit.model.remote.GroupITService;

import java.util.List;

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

    /*
        Support API callings
     */
    public Observable<AccountInfo> login(String username, String password, String grantType) {
        return mGroupITService.getToken(username, password, grantType);
    }

    public Observable<ArticlesResponse> getTopStories() {
        return mGroupITService.getTopStories();
    }

    public Observable<TestUserResponse> getUser(String token) {
        return mGroupITService.getUser(appendBearer(token));
    }

    public Observable<List<User>> getUsers(String token) {
        return mGroupITService.getUsers(appendBearer(token));
    }

    public Observable<TestTemplatesResponse> getTemplates(String token) {
        return mGroupITService.getTemplates(appendBearer(token));
    }

    public Observable<List<Note>> getInbox(String token) {
        return mGroupITService.getInbox(appendBearer(token));
    }

    public Observable<List<Note>> getSentNotes(String token) {
        return mGroupITService.getSentNotes(appendBearer(token));
    }

    private String appendBearer(String token){
        return "Bearer "+token;
    }
}
