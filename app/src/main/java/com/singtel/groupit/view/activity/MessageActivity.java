package com.singtel.groupit.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.ActivityMessageBinding;
import com.singtel.groupit.viewmodel.MessageViewModel;

/**
 * Created by razelsoco on 7/7/16.
 */

public class MessageActivity extends BaseActivity {
    private static final String EXTRA_MESSAGE="EXTRA_MESSAGE";
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_message;
    }

    public static Intent newIntent(Context c, String message){
        Intent intent = new Intent(c, MessageActivity.class);
        intent.putExtra(EXTRA_MESSAGE,message);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String message = getIntent().getStringExtra(EXTRA_MESSAGE);
        ActivityMessageBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_message);
        binding.setViewModel(new MessageViewModel(this, message));
        binding.etMessage.setText(message);
        binding.etMessage.setSelection(TextUtils.isEmpty(message)?0:message.length());
    }
}
