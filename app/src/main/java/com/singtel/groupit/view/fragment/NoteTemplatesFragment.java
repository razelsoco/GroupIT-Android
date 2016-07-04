package com.singtel.groupit.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.FragmentNoteTemplatesBinding;
import com.singtel.groupit.uiutil.DividerItemDecoration;
import com.singtel.groupit.viewmodel.NoteTemplatesViewModel;

/**
 * Created by razelsoco on 6/28/16.
 *
 */

public class NoteTemplatesFragment extends BaseMenuFragment {
    public static NoteTemplatesFragment getInstance() {
        return new NoteTemplatesFragment();
    }

    public static NoteTemplatesFragment newInstance() {
        NoteTemplatesFragment fragment = new NoteTemplatesFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_note_templates;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentNoteTemplatesBinding binding = DataBindingUtil.inflate(inflater,getLayoutRes(),container, false);
        binding.setModel(new NoteTemplatesViewModel(getActivity()));
        binding.templatesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.templatesRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.templates_divider));
        return binding.getRoot();
    }

}
