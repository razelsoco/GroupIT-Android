package com.singtel.groupit.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.singtel.groupit.model.domain.Contact;

/**
 * Created by razelsoco on 28/6/16.
 */
public class ItemSelectableContactViewModel {
    public ObservableField<String> name;
    public ObservableBoolean selectedState;
    private Contact contact;

    public ItemSelectableContactViewModel(Contact contact) {
        this.contact = contact;
        this.name = new ObservableField<>(contact.getName());
        this.selectedState = new ObservableBoolean(contact.isSelected());
    }

    public void onCheckboxClicked(View v){
        this.selectedState.set(!this.selectedState.get());
    }
}
