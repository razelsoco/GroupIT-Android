package com.singtel.groupit.model;

/**
 * Created by lanna on 6/28/16.
 {
     "sender":"Daryl Tan",
     "title":"Thanks for great help the other day. Merry Christmast to you and family!",
     "replied":true
 },
 */

public class Note {

    // FIXME data test, will update when api ready
    public String sender;
    public String title;
    public boolean replied;

    public Note() {

    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("sender=").append(sender)
//                .append(", title=").append(title)
                .append(", replied=").append(replied)
                .toString();
    }

}
