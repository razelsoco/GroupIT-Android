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

        fetchData();
    }

    private void fetchData() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = dataManager.getInbox()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(dataManager.getScheduler())
                .subscribe(new Subscriber<InboxResponse>() {
                    InboxResponse inboxResponse;
                    @Override
                    public void onCompleted() {
                        LogUtils.w(InboxViewModel.this, "onCompleted: "+ inboxResponse.notes);
                        delegate.onDataChanged(inboxResponse.notes);
                    }

                    @Override
                    public void onError(Throwable e) {
                        delegate.onError(e);
                        LogUtils.w(InboxViewModel.this, "fetchTopStories: onError: "+ e.getMessage());
                    }

                    @Override
                    public void onNext(InboxResponse inboxResponse) {
                        this.inboxResponse = inboxResponse;
                    }
                });
    }

    @Override
    public void destroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = null;
        context = null;
        delegate = null;
    }

}
