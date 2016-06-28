package com.singtel.groupit.viewmodel;


import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.StringRes;
import android.view.View;

import org.jetbrains.annotations.NotNull;

/**
 * Created by lanna on 6/28/16.
 *
 */

public class ActionBarBackViewModel {

    private Context context;

    public ObservableField<String> title;

    public ActionBarBackViewModel(@NotNull Context context) {
        this.context = context;
        this.title = new ObservableField<>();
    }

    public void setTitle(@StringRes int title) {
        setTitle(context.getString(title));
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void onClickBack(View view) {
        ((Activity) context).onBackPressed();
    }
}
