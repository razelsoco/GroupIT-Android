package com.singtel.groupit.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.FragmentNoteTemplatesBinding;
import com.singtel.groupit.uiutil.DividerItemDecoration;
import com.singtel.groupit.uiutil.ItemOffsetDecoration;
import com.singtel.groupit.viewmodel.NoteTemplatesViewModel;

/**
 * Created by razelsoco on 6/28/16.
 *
 */

public class NoteTemplatesFragment extends BaseFragment{
    public static String TAG="NoteTemplatesFragment";
    public static final int REQUEST_CODE_CREATE_MESSAGE=100;
    public static final String EXTRA_MESSAGE="extra_message";

    NoteTemplatesViewModel.Listener listener;
    FragmentNoteTemplatesBinding binding;

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
        if(binding == null) {
            binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
            binding.setModel(new NoteTemplatesViewModel(this, listener));
            binding.templatesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            binding.templatesRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.templates_divider));
            binding.templatesRecyclerView.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.dimen.space_medium, false));
            binding.tvMessage.setMovementMethod(new ScrollingMovementMethod());
        }
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (NoteTemplatesViewModel.Listener) context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            binding.getModel().setMessage(data.getStringExtra(EXTRA_MESSAGE));
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
