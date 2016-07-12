package com.singtel.groupit.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.singtel.groupit.view.fragment.NoteTemplatesFragment;

/**
 * Created by razelsoco on 8/7/16.
 */

public class MessageViewModel{
    private static final int MAX_CHARS=300;
    public ObservableField<String> characterCountLeft;
    String message;
    Context context;

    public MessageViewModel(Context c, String message) {
        this.context = c;
        this.characterCountLeft = new ObservableField<>(getCount(message));
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
        message = charSequence.toString();
        characterCountLeft.set(getCount(charSequence.toString()));
    }
    /**Binding methods end**/

    public String getCount(String message){

        int len = TextUtils.isEmpty(message)?0:message.length();
        int countLeft = MAX_CHARS - len;

        return countLeft+"";
    }
}
