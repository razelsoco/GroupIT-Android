package com.singtel.groupit.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.singtel.groupit.model.domain.User;

/**
 * Created by razelsoco on 28/6/16.
 */
public class ItemSelectableContactViewModel {
    public ObservableField<String> name;
    public ObservableBoolean selectedState;
    private User contact;

    public ItemSelectableContactViewModel(User contact) {
        this.contact = contact;
        this.name = new ObservableField<>(contact.getName());
        this.selectedState = new ObservableBoolean(contact.isSelected());
    }

    public void onCheckboxClicked(View v){
        this.selectedState.set(!this.selectedState.get());
        contact.setSelected(selectedState.get());
    }
}
