package com.singtel.groupit.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.ActivityInboxBinding;
import com.singtel.groupit.uiutil.UiUtils;
import com.singtel.groupit.view.fragment.InboxFragment;
import com.singtel.groupit.viewmodel.ActionBarBackViewModel;


/**
 * Created by lanna on 6/17/16.
 *
 */

public class InboxActivity extends BaseActivity {

    public static Intent getLaunchedIntent(Context context) {
        return new Intent(context, InboxActivity.class);
    }

    @Override
    protected int getLayoutRes() {
        return 0; // no need call setContentView(..) in BaseActivity
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInboxBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_inbox);
        ActionBarBackViewModel viewModel = new ActionBarBackViewModel(this);
        binding.setViewModel(viewModel);

        viewModel.setTitle(R.string.inbox_page_title);
        setSupportActionBar(binding.actionBarBack.toolbar);

        UiUtils.replaceFragment(this, InboxFragment.newInstance(), R.id.fragment_content, false);
    }
}
