package com.singtel.groupit.viewmodel;

import android.content.Context;
import android.support.annotation.IntDef;

import com.singtel.groupit.model.DataManager;
import com.singtel.groupit.GroupITApplication;
import com.singtel.groupit.model.NotesResponse;
import com.singtel.groupit.model.domain.Note;
import com.singtel.groupit.model.remote.ApiCommons;
import com.singtel.groupit.uiutil.OnGetDataDelegate;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Created by lanna on 6/28/16.
 *
 */

public class NotesViewModel extends RefreshingViewModel {

    public static final int PAGE_TYPE_INBOX         = 1;
    public static final int PAGE_TYPE_SENT_NOTES    = 2;


    @IntDef({PAGE_TYPE_INBOX, PAGE_TYPE_SENT_NOTES})
    public @interface NotesPageType {}

    private DataManager dataManager;
    private Subscription subscription;
    private @NotesPageType int type;

    private OnGetDataDelegate<List<Note>> delegate;

    public NotesViewModel(@NotNull Context context,
                          @NotNull OnGetDataDelegate<List<Note>> delegate,
                          @NotesPageType int type) {
        this.delegate = delegate;
        this.dataManager = GroupITApplication.get(context).getComponent().dataManager();
        this.type = type;

        onRefresh();
    }

    @Override
    public void onRefresh() {
        fetchData();
    }

    private void fetchData() {
        checkUnsubscribe();

        setRefreshing(true);
        subscription = (type == PAGE_TYPE_INBOX ? dataManager.getInbox() : dataManager.getSentNotes())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(dataManager.getScheduler())
                .subscribe(new Subscriber<NotesResponse>() {
                    NotesResponse inboxResponse;
                    @Override
                    public void onCompleted() {
//                        LogUtils.d(NotesViewModel.this, "onCompleted: "+ inboxResponse.notes);
                        setRefreshing(false);
                        delegate.onDataChanged(inboxResponse.notes);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        LogUtils.w(NotesViewModel.this, "fetchTopStories: onError: "+ e.getMessage());
                        setRefreshing(false);
                        delegate.onError(ApiCommons.logError(e));
                    }

                    @Override
                    public void onNext(NotesResponse inboxResponse) {
                        this.inboxResponse = inboxResponse;
                    }
                });
    }

    private void checkUnsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void onDestroy() {
        checkUnsubscribe();
        subscription = null;
        delegate = null;
    }

}
