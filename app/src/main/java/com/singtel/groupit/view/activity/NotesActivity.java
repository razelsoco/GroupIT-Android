package com.singtel.groupit.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.ActivityNotesBinding;
import com.singtel.groupit.uiutil.UiUtils;
import com.singtel.groupit.util.Constants;
import com.singtel.groupit.view.fragment.NotesFragment;
import com.singtel.groupit.viewmodel.ActionBarBackViewModel;
import com.singtel.groupit.viewmodel.NotesViewModel;


/**
 * Created by lanna on 6/17/16.
 *
 */

public class NotesActivity extends BaseActivity {

    public static Intent getLaunchedIntent(Context context, @NotesViewModel.NotesPageType int type) {
        Intent intent = new Intent(context, NotesActivity.class);
        intent.putExtra(Constants.NOTES_PAGE_TYPE, type);
        return intent;
    }

    @Override
    protected int getLayoutRes() {
        return 0; // no need call setContentView(..) in BaseActivity
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNotesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_notes);
        ActionBarBackViewModel viewModel = new ActionBarBackViewModel(this);
        binding.setViewModel(viewModel);

        @NotesViewModel.NotesPageType int type = getIntent().getIntExtra(Constants.NOTES_PAGE_TYPE, NotesViewModel.PAGE_TYPE_INBOX);
        viewModel.setTitle(type == NotesViewModel.PAGE_TYPE_INBOX ? R.string.inbox : R.string.sent_notes);
        setSupportActionBar(binding.actionBarBack.toolbar);

        UiUtils.replaceFragment(this, NotesFragment.newInstance(type), R.id.fragment_content, false);
    }
}
