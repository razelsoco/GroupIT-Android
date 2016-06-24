package com.singtel.groupit.viewmodel;

import android.databinding.ObservableField;
import android.support.v4.app.Fragment;
import android.view.View;

import com.singtel.groupit.R;
import com.singtel.groupit.uiutil.UiUtils;
import com.singtel.groupit.view.fragment.NotesFragment;
import com.singtel.groupit.view.fragment.SettingsFragment;

/**
 * Created by razelsoco on 21/6/16.
 */

public class DashBoardViewModel {
    public ObservableField<String> userName;
    public ObservableField<String> userGroup;

    private Fragment mFragment;
    public DashBoardViewModel(Fragment fragment) {
        this.userName =  new ObservableField<>("Razel S");
        this.userGroup =  new ObservableField<>("Group IT");
        this.mFragment = fragment;
    }

    public void onNotesClick(View view){
        UiUtils.replaceFragment(mFragment.getActivity(), NotesFragment.class.getName(), NotesFragment.newInstance(), R.id.menu_frame);
    }

    public void onSettingsClick(View view){
        UiUtils.replaceFragment(mFragment.getActivity(), SettingsFragment.class.getName(), SettingsFragment.newInstance(), R.id.menu_frame);
    }

}
