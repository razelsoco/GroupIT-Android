package com.singtel.groupit.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.singtel.groupit.model.remote.ApiStatusCode;
import com.singtel.groupit.model.Response;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;

public class NetworkUtils {

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnectedOrConnecting());
    }

	public static boolean statusOK(Response res) {
		if(res == null) return false;
		if(res.getStatus() == null) return false;
		if(res.getStatus().getCode() == ApiStatusCode.STATUS_OK || res.getStatus().getCode() == ApiStatusCode.STATUS_CREATED) return true;
		return false;
	}
	
	public static boolean deviceStatusOK(Response res) {
		if(res == null) return false;
		if(res.getStatus() == null) return false;
		if(res.getStatus().getCode() == ApiStatusCode.STATUS_OK || res.getStatus().getCode() == ApiStatusCode.STATUS_DEVICE_ALREADY_REGISTERED || res.getStatus().getCode() == ApiStatusCode.STATUS_CREATED) return true;
		return false;
	}

    public static Bundle parseUrl(String url) {
        try {
            URL u = new URL(url);
            Bundle b = decodeUrl(u.getQuery());
            b.putAll(decodeUrl(u.getRef()));
            return b;
        } catch (MalformedURLException e) {
            return new Bundle();
        }
    }

    public static Bundle decodeUrl(String s) {
        Bundle params = new Bundle();
        if (s != null) {
            String array[] = s.split("&");
            for (String parameter : array) {
                String v[] = parameter.split("=");
                if (v.length == 2) {
                    params.putString(URLDecoder.decode(v[0]),
                            URLDecoder.decode(v[1]));
                }
            }
        }
        return params;
    }

    public static String getEncodedURL(String urlStr) {
        try {
            URL url = new URL(urlStr);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            return uri.toURL().toString();
        } catch (Exception e) {
            return urlStr;
        }
    }

    public static boolean isConnectedMobile(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }
	
}
