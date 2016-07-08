package com.singtel.groupit.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.FragmentRecipientsBinding;
import com.singtel.groupit.model.domain.User;
import com.singtel.groupit.uiutil.DividerItemDecoration;
import com.singtel.groupit.uiutil.ItemOffsetDecoration;
import com.singtel.groupit.view.activity.SelectableContactsActivity;
import com.singtel.groupit.viewmodel.RecipientsViewModel;

import java.util.ArrayList;

/**
 * Created by razelsoco on 6/23/16.
 *
 */

public class RecipientsFragment extends BaseMenuFragment {
    public static final int REQUEST_CODE_SELECT_USERS=100;
    FragmentRecipientsBinding binding;
    public static RecipientsFragment getInstance() {
        return new RecipientsFragment();
    }

    public static RecipientsFragment newInstance() {
        RecipientsFragment fragment = new RecipientsFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recipients;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(binding == null) {
            binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
            binding.setModel(new RecipientsViewModel(this));
            binding.recepientsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.recepientsRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.divider_silver));
            binding.filteredContactsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.filteredContactsRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.divider_silver));
            binding.filteredContactsRecyclerView.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.dimen.space_medium, false));
        }
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_SELECT_USERS && resultCode == Activity.RESULT_OK) {
            ArrayList<User> users = data.getExtras().getParcelableArrayList(SelectableContactsActivity.EXTRA_ALL_USERS);
            binding.getModel().filterSelectedUsers(users);
        }else
            super.onActivityResult(requestCode, resultCode, data);
    }
}
