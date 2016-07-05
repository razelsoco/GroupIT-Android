package com.singtel.groupit.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.FragmentMenuNotesBinding;
import com.singtel.groupit.databinding.FragmentNotesBinding;
import com.singtel.groupit.view.activity.NotesActivity;
import com.singtel.groupit.view.activity.SendNoteActivity;
import com.singtel.groupit.viewmodel.NotesViewModel;

/**
 * Created by razelsoco on 6/23/16.
 *
 */

public class MenuNotesFragment extends BaseMenuFragment {
    public static MenuNotesFragment getInstance() {
        return new MenuNotesFragment();
    }

    public static MenuNotesFragment newInstance() {
        MenuNotesFragment fragment = new MenuNotesFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_menu_notes;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentMenuNotesBinding binding = DataBindingUtil.inflate(inflater,getLayoutRes(),container, false);
        binding.setFragment(this);
        return binding.getRoot();
    }

    public void onBackClick(View view){
        onBackPressed();
    }

    public void onSendNoteClick(View view){
        startActivity(SendNoteActivity.newIntent(getActivity()));
    }

    public void onInboxClick(View view) {
        getActivity().startActivity(
                NotesActivity.getLaunchedIntent(getActivity(), NotesViewModel.PAGE_TYPE_INBOX));
    }

    public void onSentClick(View view) {
        getActivity().startActivity(
                NotesActivity.getLaunchedIntent(getActivity(), NotesViewModel.PAGE_TYPE_SENT_NOTES));
    }
}