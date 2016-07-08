package com.singtel.groupit.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.ActivityMessageBinding;
import com.singtel.groupit.viewmodel.MessageViewModel;

/**
 * Created by razelsoco on 7/7/16.
 */

public class MessageActivity extends BaseActivity {
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_message;
    }

    public static Intent newIntent(Context c){
        Intent intent = new Intent(c, MessageActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMessageBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_message);
        binding.setViewModel(new MessageViewModel(this));
    }
}
