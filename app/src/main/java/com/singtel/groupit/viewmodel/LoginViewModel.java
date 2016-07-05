package com.singtel.groupit.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.text.TextWatcher;

import com.singtel.groupit.DataManager;
import com.singtel.groupit.GroupITApplication;
import com.singtel.groupit.model.AccountInfo;
import com.singtel.groupit.uiutil.OnGetDataDelegate;
import com.singtel.groupit.uiutil.SimpleTextWatcher;
import com.singtel.groupit.util.KeystoreUtil;

import org.jetbrains.annotations.NotNull;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * Created by lanna on 6/28/16.
 *
 */

public class LoginViewModel implements ViewModel {

    public final ObservableInt loadingVisible;

    private DataManager dataManager;
    private Subscription subscription;

    private OnGetDataDelegate<AccountInfo> delegate;

    private String username;
    private String password;

    public LoginViewModel(@NotNull Context context, @NotNull OnGetDataDelegate<AccountInfo> delegate) {
        this.delegate = delegate;
        this.dataManager = GroupITApplication.get(context).getComponent().dataManager();

        this.loadingVisible = new ObservableInt(GONE);

        // FIXME remove test
//        username = "super.admin@2359media.com";
//        password = "Admin@123456";
//        checkLogin();

        KeystoreUtil.test(context);
    }

    /*
        Data Binding
     */

    public TextWatcher getOnEmailChanged() {
        return new SimpleTextWatcher() {

            @Override
            public void onTextChanged(String text) {
                username = text;
                checkLogin();
            }
        };
    }

    public TextWatcher getOnPasswordChanged() {
        return new SimpleTextWatcher() {

            @Override
            public void onTextChanged(String text) {
                password = text;
                checkLogin();
            }
        };
    }

    /*
        Functions
     */

    private void checkLogin() {
        if (password == null || password.length() != 12) {
            return;
        }

        checkUnsubscribe();

        loadingVisible.set(VISIBLE);
        subscription = dataManager.login(username, password, "password")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(dataManager.getScheduler())
                .subscribe(new Subscriber<AccountInfo>() {
                    AccountInfo account;
                    @Override
                    public void onCompleted() {
                        loadingVisible.set(GONE);
                        storeUserData(account);
                        delegate.onDataChanged(account);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingVisible.set(GONE);
                        delegate.onError(e);
                    }

                    @Override
                    public void onNext(AccountInfo account) {
                        this.account = account;
                    }
                });
    }

    private void storeUserData(AccountInfo account) {

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
