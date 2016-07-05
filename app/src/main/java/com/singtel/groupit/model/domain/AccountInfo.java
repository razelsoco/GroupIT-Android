package com.singtel.groupit.model.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lanna on 6/29/16.
 {
 "access_token": "y3QC75hFrpjxzPGUPoBQjBQKCjyPAh7KgAwhnCgXWz9WqCVcVkJZ07uJ-...",
 "token_type": "bearer",
 "expires_in": 1209599,
 "userName": "super.admin@2359media.com",
 ".issued": "Wed, 29 Jun 2016 10:35:02 GMT",
 ".expires": "Wed, 13 Jul 2016 10:35:02 GMT"
 }
 */

public class AccountInfo {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private String expiresIn;

    private String userName;

    @Override
    public String toString() {
        return new StringBuilder()
                .append("userName=").append(userName)
                .append(", tokenType=").append(tokenType)
                .append(", expiresIn=").append(expiresIn)
                .append(", accessToken=").append(accessToken)
                .toString();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
