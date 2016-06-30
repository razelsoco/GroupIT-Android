package com.singtel.groupit.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.singtel.groupit.R;
import com.singtel.groupit.uiutil.UiUtils;
import com.singtel.groupit.view.fragment.MainFragment;
import com.singtel.groupit.view.fragment.RecepientsFragment;

/**
 * Created by razelsoco on 27/6/16.
 */

public class SendNoteActivity extends BaseActivity {
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_sendnote;
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, SendNoteActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiUtils.replaceFragment(this, RecepientsFragment.newInstance(), R.id.fragment_content, false);
    }
}
