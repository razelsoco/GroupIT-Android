package com.singtel.groupit.viewmodel;

import android.databinding.ObservableField;

import com.singtel.groupit.model.domain.Contact;

/**
 * Created by razelsoco on 28/6/16.
 */
public class ItemRecepientViewModel {
    public ObservableField<String> name;

    public Contact contact;

    public ItemRecepientViewModel(Contact contact) {
        this.contact = contact;
        this.name = new ObservableField<>(contact.getName());
    }


}
