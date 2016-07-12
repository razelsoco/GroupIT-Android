package com.singtel.groupit.model.remote;

import com.singtel.groupit.util.LogUtils;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;


/**
 * Created by lanna on 6/23/16.
 *
 */

public class ApiCommons {

    public static final byte COLOR_FORMAT_LENGTH = 7;

    public static boolean colorValid(String color) {
        return color != null && color.length() == ApiCommons.COLOR_FORMAT_LENGTH;
    }

    /*
    HTTP Status: 400 BAD REQUEST
    {
      "error": {
        "code": "600",
        "title": "Bad request",
        "detail": "The specified email is malformed."
      }
    }
     */
    public static String parseErrorMessage(Throwable throwable) {
        RetrofitException error = (RetrofitException) throwable;

        String message;
        if (error.getKind() == RetrofitException.Kind.HTTP) {
            // We had non-2XX http error
            message = "We had non-2XX http error: " + error.getMessage();
        }
        else if (error.getKind() == RetrofitException.Kind.NETWORK) {
            // A network or conversion error happened
            message = "A network or conversion error happened: " + error.getMessage();
        }
        else {
            // We don't know what happened. We need to simply convert to an unknown error
            // ...
            message = "We don't know what happened. We need to simply convert to an unknown error: " + error.getMessage();
        }

        LogUtils.w("ws-error", "parseErrorMessage: url=" + error.getUrl()
                + "\nkind=" + error.getKind() + ", response=" + error.getResponse()
                + "\nmessage: "+ message);
        return message;
    }
}
