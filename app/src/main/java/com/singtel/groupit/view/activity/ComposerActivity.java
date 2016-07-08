package com.singtel.groupit.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.ActivityComposerBinding;
import com.singtel.groupit.uiutil.UiUtils;
import com.singtel.groupit.view.fragment.NoteTemplatesFragment;
import com.singtel.groupit.view.fragment.RecipientsFragment;
import com.singtel.groupit.viewmodel.ComposerViewModel;
import com.singtel.groupit.viewmodel.NoteTemplatesViewModel;

/**
 * Created by razelsoco on 27/6/16.
 */

public class ComposerActivity extends BaseActivity implements NoteTemplatesViewModel.Listener{
    ActivityComposerBinding binding;
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_composer;
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, ComposerActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_composer);
        binding.setViewModel(new ComposerViewModel(this));

        UiUtils.replaceFragment(this, RecipientsFragment.newInstance(), R.id.fragment_content, false);
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }

    @Override
    public void onMessageChanged(String message) {
        binding.getViewModel().setRightButtonEnabled(message);
    }
}
