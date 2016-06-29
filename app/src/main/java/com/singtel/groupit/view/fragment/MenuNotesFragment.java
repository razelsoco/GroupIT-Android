package com.singtel.groupit.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.FragmentNotesBinding;
import com.singtel.groupit.view.activity.InboxActivity;

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
        Toast.makeText(getActivity(),"send a note", Toast.LENGTH_SHORT).show();
    }

    public void onInboxClick(View view) {
        getActivity().startActivity(
                InboxActivity.getLaunchedIntent(getActivity()));
    }

    public void onSentClick(View view){
        Toast.makeText(getActivity(),"sent", Toast.LENGTH_SHORT).show();
    }
}
