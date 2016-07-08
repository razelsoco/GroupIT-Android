package com.singtel.groupit.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.view.View;

import com.singtel.groupit.view.fragment.NoteTemplatesFragment;

/**
 * Created by razelsoco on 8/7/16.
 */

public class MessageViewModel{
    public ObservableField<String> characterCountLeft;
    String message;
    Context context;

    public MessageViewModel(Context c, String message) {
        this.context = c;
        this.characterCountLeft = new ObservableField<>("300");
        this.message = message;
    }

    /**Binding methods start**/
    public void onDoneClicked(View v){
        Intent i = new Intent();
        i.putExtra(NoteTemplatesFragment.EXTRA_MESSAGE, message);
        ((Activity)context).setResult(Activity.RESULT_OK, i);
        ((Activity)context).finish();
    }

    public void onCancelClicked(View v){
        ((Activity)context).finish();
    }

    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        int countLeft = 300 - charSequence.length();
        message = charSequence.toString();
        characterCountLeft.set(countLeft+"");
    }
    /**Binding methods end**/

}
