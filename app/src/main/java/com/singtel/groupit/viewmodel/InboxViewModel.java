package com.singtel.groupit.viewmodel;

import android.content.Context;

import com.singtel.groupit.DataManager;
import com.singtel.groupit.GroupITApplication;
import com.singtel.groupit.model.InboxResponse;
import com.singtel.groupit.model.Note;
import com.singtel.groupit.util.LogUtils;
import com.singtel.groupit.view.fragment.MainFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by lanna on 6/28/16.
 *
 */

public class InboxViewModel implements ViewModel {

    private Context context;

    private DataManager dataManager;
    private Subscription subscription;

    public interface InboxDelegate {
        void onStartGetData();
        void onDataChanged(List<Note> notes);
        void onError(Throwable e);
    }
    private InboxDelegate delegate;

    public InboxViewModel(@NotNull Context context, @NotNull InboxDelegate delegate) {
        this.delegate = delegate;
        dataManager = GroupITApplication.get(context).getComponent().dataManager();

        onRefresh();
    }

    public void onRefresh() {
        fetchData();
    }

    private void fetchData() {
        checkUnsubscribe();

        delegate.onStartGetData();
        subscription = dataManager.getInbox()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(dataManager.getScheduler())
                .subscribe(new Subscriber<InboxResponse>() {
                    InboxResponse inboxResponse;
                    @Override
                    public void onCompleted() {
//                        LogUtils.d(InboxViewModel.this, "onCompleted: "+ inboxResponse.notes);
                        delegate.onDataChanged(inboxResponse.notes);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        LogUtils.w(InboxViewModel.this, "fetchTopStories: onError: "+ e.getMessage());
                        delegate.onError(e);
                    }

                    @Override
                    public void onNext(InboxResponse inboxResponse) {
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
    public void destroy() {
        checkUnsubscribe();
        subscription = null;
        context = null;
        delegate = null;
    }

}
