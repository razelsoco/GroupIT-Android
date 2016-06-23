package com.singtel.groupit.data.remote;

/**
 * Created by lanna on 6/23/16.
 *
 */

public class ApiCommons {

    public static final byte COLOR_FORMAT_LENGTH = 7;

    public static boolean colorValid(String color) {
        return color != null && color.length() == ApiCommons.COLOR_FORMAT_LENGTH;
    }
}
