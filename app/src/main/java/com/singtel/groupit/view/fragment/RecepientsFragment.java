package com.singtel.groupit.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.FragmentRecepientsBinding;
import com.singtel.groupit.uiutil.DividerItemDecoration;
import com.singtel.groupit.uiutil.ItemOffsetDecoration;
import com.singtel.groupit.viewmodel.RecepientsViewModel;

/**
 * Created by razelsoco on 6/23/16.
 *
 */

public class RecepientsFragment extends BaseMenuFragment {
    public static RecepientsFragment getInstance() {
        return new RecepientsFragment();
    }

    public static RecepientsFragment newInstance() {
        RecepientsFragment fragment = new RecepientsFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recepients;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentRecepientsBinding binding = DataBindingUtil.inflate(inflater,getLayoutRes(),container, false);
        binding.setModel(new RecepientsViewModel(getActivity()));
        binding.recepientsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recepientsRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.divider_silver));
        binding.filteredContactsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.filteredContactsRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.divider_silver));
        binding.filteredContactsRecyclerView.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.dimen.space_medium, false));

        return binding.getRoot();
    }

}
