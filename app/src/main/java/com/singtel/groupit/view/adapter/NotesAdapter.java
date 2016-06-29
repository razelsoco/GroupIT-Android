package com.singtel.groupit.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.singtel.groupit.R;
import com.singtel.groupit.databinding.ItemNoteBinding;
import com.singtel.groupit.model.Note;
import com.singtel.groupit.viewmodel.NoteViewModel;

/**
 * Created by lanna on 6/22/16.
 *
 */

public class NotesAdapter extends BaseRecyclerAdapter<Note, NotesAdapter.BindingHolder> {

    public NotesAdapter() {

    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemNoteBinding itemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_note, parent, false);

        return new BindingHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.binding.setViewModel(new NoteViewModel(getItem(position)));
    }

    /*
        View Holder
     */

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ItemNoteBinding binding;

        public BindingHolder(ItemNoteBinding binding) {
            super(binding.rootView);
            this.binding = binding;
        }
    }
}
