package com.singtel.groupit.uiutil;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by lanna on 6/29/16.
 *
 */

public abstract class SimpleTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        onTextChanged(s.toString());
    }

    public abstract void onTextChanged(String newText);

}
