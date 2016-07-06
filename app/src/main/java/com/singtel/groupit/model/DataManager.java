package com.singtel.groupit.model;

import android.content.Context;

import com.singtel.groupit.GroupITApplication;
import com.singtel.groupit.injection.component.DaggerDataManagerComponent;
import com.singtel.groupit.injection.module.DataManagerModule;
import com.singtel.groupit.model.domain.AccountInfo;
import com.singtel.groupit.model.remote.GroupITService;
import com.singtel.groupit.util.LogUtils;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.HttpException;
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

    public Observable<TestUserResponse> getUser() {
        return mGroupITService.getUser();
    }

    public Observable<TestContactsResponse> getContacts() {
        return mGroupITService.getContacts();
    }

    public Observable<TestTemplatesResponse> getTemplates() {
        return mGroupITService.getTemplates();
    }

    public Observable<NotesResponse> getInbox() {
        return mGroupITService.getInbox();
    }

    public Observable<NotesResponse> getSentNotes() {
        return mGroupITService.getSentNotes();
    }

    /*
    HTTP Status: 400 BAD REQUEST
    {
      "error": {
        "code": "600",
        "title": "Bad request",
        "detail": "The specified email is malformed."
      }
    }
     */
    public String logError(Throwable throwable) {
        String message;
        if (throwable instanceof HttpException) {
            // We had non-2XX http error
            message = "We had non-2XX http error: " + ((HttpException) throwable).response().message();
        }
        else if (throwable instanceof IOException) {
            // A network or conversion error happened
            message = "A network or conversion error happened: " + throwable.getMessage();
        }
        else {
            // We don't know what happened. We need to simply convert to an unknown error
            // ...
            message = "We don't know what happened. We need to simply convert to an unknown error: " + throwable.getMessage();
        }
        LogUtils.d(this, "logError: " + message);
        return message;
    }
}
