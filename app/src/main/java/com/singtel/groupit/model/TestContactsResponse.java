package com.singtel.groupit.model;

import com.google.gson.annotations.SerializedName;
import com.singtel.groupit.model.domain.Contact;

import java.util.ArrayList;

/**
 * Created by razelsoco on 27/6/16.
 */

public class TestContactsResponse {
    @SerializedName("contacts")
    public ArrayList<Contact> contacts;
}
