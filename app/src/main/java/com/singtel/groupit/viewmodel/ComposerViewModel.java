package com.singtel.groupit.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

import com.singtel.groupit.R;
import com.singtel.groupit.model.domain.NewNoteSession;
import com.singtel.groupit.uiutil.UiUtils;
import com.singtel.groupit.view.activity.ComposerActivity;
import com.singtel.groupit.view.fragment.NoteTemplatesFragment;
import com.singtel.groupit.view.fragment.RecipientsFragment;

/**
 * Created by razelsoco on 8/7/16.
 */

public class ComposerViewModel {
    public ObservableField<String> rightButtonText;
    public ObservableBoolean isRightButtonEnabled;
    String currentSreen;
    Context context;
    NewNoteSession newNoteSession;

    public ComposerViewModel(Context c) {
        this.context = c;
        this.currentSreen = RecipientsFragment.TAG;
        this.rightButtonText = new ObservableField<>(context.getString(R.string.next));
        this.isRightButtonEnabled = new ObservableBoolean(true);
        this.newNoteSession = NewNoteSession.getInstance();
    }

    /**Binding methods start**/
    public void onLeftButtonClicked(View v){
        ((Activity)context).onBackPressed();
        this.currentSreen = RecipientsFragment.TAG;
        this.rightButtonText.set(context.getString(R.string.next));
    }

    public void onRightButtonClicked(View v){
        //if recipients screen,
        if(currentSreen == RecipientsFragment.TAG) {
            UiUtils.replaceFragment((FragmentActivity) context, NoteTemplatesFragment.newInstance(), R.id.fragment_content, true);
            this.rightButtonText.set(context.getString(R.string.send));
            this.currentSreen = NoteTemplatesFragment.TAG;
        }else{
            //send note to server

        }

    }

    /**Binding methods end**/

    /**
     * Called from {@link ComposerActivity} when {@link NoteTemplatesViewModel}
     * notifies that user has typed a message
     * */
    public void setRightButtonEnabled(String message){
        if(TextUtils.isEmpty(message))
            this.isRightButtonEnabled.set(false);
        else
            this.isRightButtonEnabled.set(true);
    }

}
