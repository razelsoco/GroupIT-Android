package com.singtel.groupit.viewmodel;

import android.databinding.ObservableField;

import com.singtel.groupit.model.domain.User;

/**
 * Created by razelsoco on 28/6/16.
 */
public class ItemRecepientViewModel {
    public ObservableField<String> name;

    public User user;

    public ItemRecepientViewModel(User user) {
        this.user = user;
        this.name = new ObservableField<>(user.getName());
    }


}
