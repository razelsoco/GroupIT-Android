package com.singtel.groupit.viewmodel;

import android.databinding.ObservableField;
import android.support.v4.app.Fragment;
import android.view.View;

import com.singtel.groupit.view.fragment.BaseMenuFragment;
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
        ((BaseMenuFragment)this.mFragment).replaceMenuFragment(NotesFragment.class.getName(), NotesFragment.newInstance());
    }

    public void onSettingsClick(View view){
        ((BaseMenuFragment)this.mFragment).replaceMenuFragment(SettingsFragment.class.getName(), SettingsFragment.newInstance());
    }

}
