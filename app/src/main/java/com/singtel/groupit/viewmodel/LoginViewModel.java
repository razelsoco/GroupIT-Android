package com.singtel.groupit.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextWatcher;

import com.singtel.groupit.BuildConfig;
import com.singtel.groupit.GroupITApplication;
import com.singtel.groupit.R;
import com.singtel.groupit.model.DataManager;
import com.singtel.groupit.model.domain.AccountInfo;
import com.singtel.groupit.model.remote.ApiCommons;
import com.singtel.groupit.uiutil.OnGetDataDelegate;
import com.singtel.groupit.uiutil.SimpleTextWatcher;
import com.singtel.groupit.model.local.GroupITSharedPreferences;
import com.singtel.groupit.util.LogUtils;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

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

    private Context context;

    /// dagger inject single instances
    @Inject
    GroupITSharedPreferences pref;
    @Inject
    DataManager dataManager;
    Subscription subscription;

    /// data binding ui values
    public final ObservableInt loadingVisible;
    public final ObservableField<String> username;
    public final ObservableField<String> password;

    /// callback
    private OnGetDataDelegate<AccountInfo> delegate;

    public LoginViewModel(@NotNull Context context, @NotNull OnGetDataDelegate<AccountInfo> delegate) {
        this.context = context;
        this.delegate = delegate;

//        this.dataManager = GroupITApplication.get(context).getComponent().dataManager();
        GroupITApplication.get(context).getComponent().inject(this);

        this.loadingVisible = new ObservableInt(GONE);
        this.username = new ObservableField<>("");
        this.password = new ObservableField<>("");

        // FIXME remove test
        if (BuildConfig.DEBUG) {
            username.set("super.admin@2359media.com");
            password.set("Admin@123456");
//            KeystoreUtil.test(context, "name", "lanna123");
        }
    }

    /*
        Data Binding
     */

    public TextWatcher getOnEmailChanged() {
        return new SimpleTextWatcher() {

            @Override
            public void onTextChanged(String text) {
                username.set(text);
                checkLogin();
            }
        };
    }

    public TextWatcher getOnPasswordChanged() {
        return new SimpleTextWatcher() {

            @Override
            public void onTextChanged(String text) {
                password.set(text);
                checkLogin();
            }
        };
    }

    /*
        Functions
     */

    private void checkLogin() {
        if (password == null || password.get().length() != 12) {
            return;
        }

        checkUnsubscribe();

        loadingVisible.set(VISIBLE);
        subscription = dataManager.login(username.get(), password.get(), "password")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(dataManager.getScheduler())
                .subscribe(new Subscriber<AccountInfo>() {
                    AccountInfo account;
                    @Override
                    public void onCompleted() {
                        LogUtils.i(LoginViewModel.this, "onCompleted: " + account);
                        loadingVisible.set(GONE);
                        storeUserData(account);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingVisible.set(GONE);
                        delegate.onError(ApiCommons.parseErrorMessage(e));
                    }

                    @Override
                    public void onNext(AccountInfo account) {
                        this.account = account;
                    }
                });
    }

    private void storeUserData(AccountInfo account) {
        boolean saveSuccess = pref.saveUserToken(account.getAccessToken());
        if (saveSuccess) {
            delegate.onDataChanged(account);
        } else {
            delegate.onError(context.getString(R.string.save_data_error));
        }
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
