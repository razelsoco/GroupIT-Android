package com.singtel.groupit.model;

import com.google.gson.annotations.SerializedName;
import com.singtel.groupit.model.domain.User;

/**
 * Created by razelsoco on 27/6/16.
 */

public class TestUserResponse {
    @SerializedName("user")
    public User user;
}
