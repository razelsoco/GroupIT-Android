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

public class NotesFragment extends BaseMenuFragment {
    public static NotesFragment getInstance() {
        return new NotesFragment();
    }

    public static NotesFragment newInstance() {
        NotesFragment fragment = new NotesFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_notes;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentNotesBinding binding = DataBindingUtil.inflate(inflater,getLayoutRes(),container, false);
        binding.setFragment(this);
        return binding.getRoot();
    }

//    public void onClick(View view){
//        switch (view.getId()){
//            case R.id.bt_back:
//                getFragmentManager().popBackStack();
//                break;
//            case R.id.tv_send_note:
//                Toast.makeText(getActivity(),"send a note", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.tv_inbox:
//                Toast.makeText(getActivity(),"inbox", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.tv_sent:
//                Toast.makeText(getActivity(),"sent", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }

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
