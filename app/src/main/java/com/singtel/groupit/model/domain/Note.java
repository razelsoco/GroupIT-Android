package com.singtel.groupit.model.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lanna on 6/28/16.
 {
     "sender":"Daryl Tan",
     "title":"Thanks for great help the other day. Merry Christmast to you and family!",
     "replied":true
 },

 {
     "Id": 1,
     "UserId": "fd304279-a62d-41ee-ac3a-48a2107aabe5",
     "FullName": null,
     "Message": "This is a test message. Please do not reply",
     "Replies": [],
     "ImageUrl": "http://groupit-staging.ap-southeast-1.elasticbeanstalk.com/api/Images/1"
 }
 */

public class Note {

    @SerializedName("Id")
    private String id;
    @SerializedName("UserId")
    private String userId;
    @SerializedName("FullName")
    private String fullName;
    @SerializedName("Message")
    private String message;
    @SerializedName("Replies")
    private List replies; // TODO model structure ???
    @SerializedName("ImageUrl")
    private String imageUrl;

    public Note() {

    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("id=").append(id)
                .append(", userId=").append(userId)
                .append(", fullName=").append(fullName)
                .append(", message=").append(message)
                .append(", replies=").append(replies)
                .append(", imageUrl=").append(imageUrl)
                .toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List getReplies() {
        return replies;
    }

    public void setReplies(List replies) {
        this.replies = replies;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
