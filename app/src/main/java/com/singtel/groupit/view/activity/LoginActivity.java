package com.singtel.groupit.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.ActivityLoginBinding;
import com.singtel.groupit.model.domain.AccountInfo;
import com.singtel.groupit.uiutil.OnGetDataDelegate;
import com.singtel.groupit.uiutil.UiUtils;
import com.singtel.groupit.util.LogUtils;
import com.singtel.groupit.viewmodel.LoginViewModel;

/**
 * Created by lanna on 6/17/16.
 *
 */

public class LoginActivity extends BaseActivity implements OnGetDataDelegate<AccountInfo> {


    @Override
    protected int getLayoutRes() {
        return 0; // return 0 to not use setContentView on BaseActivity
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setViewModel(new LoginViewModel(this, this));
    }

    @Override
    public void onDataChanged(AccountInfo data) {
//        LogUtils.i(this, "onDataChanged: " + data);
        UiUtils.makeToastShort(this, getString(R.string.login_success));
        gotoMainScreen();
    }

    @Override
    public void onError(String message) {
//        LogUtils.i(this, "onError: " + message);
        UiUtils.makeToastShort(this, "Error: " + message);
    }

    private void gotoMainScreen() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
