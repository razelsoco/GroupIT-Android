package com.singtel.groupit.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.singtel.groupit.model.domain.Note;

/**
 * Created by lanna on 6/28/16.
 *
 */

public class NoteViewModel {

    public ObservableInt replyNoteVisibility;
    public ObservableField<String> title;
    public ObservableField<String> content;


    private NoteViewModel() {
        replyNoteVisibility = new ObservableInt(View.INVISIBLE);
        title = new ObservableField<>();
        content = new ObservableField<>();
    }

    public NoteViewModel(Note note) {
        this();
        title.set(note.getFullName());
        content.set(note.getMessage());
        replyNoteVisibility.set(note.getReplies() != null && note.getReplies().size() > 0 ?
                View.VISIBLE : View.INVISIBLE);
    }
}
