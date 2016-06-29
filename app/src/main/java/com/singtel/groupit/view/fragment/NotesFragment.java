package com.singtel.groupit.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.FragmentNotesBinding;
import com.singtel.groupit.model.Note;
import com.singtel.groupit.uiutil.DividerItemDecoration;
import com.singtel.groupit.uiutil.UiUtils;
import com.singtel.groupit.util.Constants;
import com.singtel.groupit.util.LogUtils;
import com.singtel.groupit.view.adapter.NotesAdapter;
import com.singtel.groupit.viewmodel.NotesViewModel;

import java.util.List;


/**
 * Created by lanna on 6/28/16.
 *
 */

public class NotesFragment extends BaseFragment
        implements NotesViewModel.NotesDelegate, SwipeRefreshLayout.OnRefreshListener {

    private FragmentNotesBinding binding;
    private NotesAdapter adapter;


    public static NotesFragment newInstance(@NotesViewModel.NotesPageType int type) {
        NotesFragment fragment = new NotesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.NOTES_PAGE_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new NotesAdapter();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_notes;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        @NotesViewModel.NotesPageType int type = getArguments().getInt(Constants.NOTES_PAGE_TYPE);
        binding.setViewModel(new NotesViewModel(getContext(), this, type));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.swipeRefreshRecyclerView.swipeRefreshContainer.setOnRefreshListener(this);
        setupRecyclerView(binding.swipeRefreshRecyclerView.recyclerView);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), R.drawable.divider_vertical_light));
    }

    @Override
    public void onRefresh() {
        binding.getViewModel().onRefresh();
    }

    @Override
    public void onStartGetData() {
        LogUtils.i(this, "onStartGetData: ");
        UiUtils.doWhenViewHasSize(binding.swipeRefreshRecyclerView.swipeRefreshContainer,
                new Runnable() {
                    @Override
                    public void run() {
                        binding.swipeRefreshRecyclerView.swipeRefreshContainer.setRefreshing(true);
                    }
                });
    }

    @Override
    public void onDataChanged(List<Note> notes) {
        LogUtils.i(this, "onDataChanged: " + notes);
        binding.swipeRefreshRecyclerView.swipeRefreshContainer.setRefreshing(false);
        adapter.setItemsAndNotify(notes);
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.i(this, "onError: " + e.getMessage());
        binding.swipeRefreshRecyclerView.swipeRefreshContainer.setRefreshing(false);
        UiUtils.makeToastShort(getContext(), "Error: " + e.getMessage());
    }
}
