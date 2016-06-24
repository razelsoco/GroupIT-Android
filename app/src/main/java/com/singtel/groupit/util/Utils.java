package com.singtel.groupit.util;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Utils {


    public static String md5(String token) {
        byte[] plainText = null;
        MessageDigest md = null;

        try {
            plainText = token.getBytes("UTF-8");
            md = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
        md.reset();
        md.update(plainText);
        byte[] digest = md.digest();
        BigInteger bitInt = new BigInteger(1, digest);
        return bitInt.toString(16);
    }

    public static void appendLog(String text) {
        appendLog(text, "log.json");
    }

    public static void appendLog(String text, String fileName) {
        File logFile = new File(Environment.getExternalStorageDirectory() + "/"
                + fileName);
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            // BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,
                    false));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void clearCookies(Context context) {
        try {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0
                || "null".equals(string);// || string.contains("null");
    }



    public static boolean isInIntegerArray(int key, ArrayList<Integer> mustMatchList) {
        boolean isInMustMatchList = false;
        for (int i = 0; i < mustMatchList.size(); i++) {
            if (mustMatchList.get(i) == key) {
                isInMustMatchList = true;
                break;
            }
        }
        return isInMustMatchList;
    }

    public static String convertArrayToString(List<String> list) {
        return convertArrayToString(list, ",");
    }

    public static String convertArrayToString(List<String> list, String separator) {
        if (list == null || list.size() == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder(getNonNullText(list.get(0)));
        for (int i = 1; i < list.size(); i++) {
            sb.append(separator).append(getNonNullText(list.get(i)));
        }
        return sb.toString();
    }

    public static ArrayList<String> parseListFromString(String valuesString) {
        return parseListFromString(valuesString, ",");
    }

    public static ArrayList<String> parseListFromString(String valuesString, String separator) {
        if (TextUtils.isEmpty(valuesString)) {
            return null;
        }

        String[] array = valuesString.split(separator);
        return new ArrayList<String>(Arrays.asList(array));
    }

    public static String getNonNullText(String text) {
        return text == null ? "" : text;
    }



}
