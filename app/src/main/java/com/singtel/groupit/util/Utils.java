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


    public static <T> void initLoader(final int loaderId, final Bundle args,
                                      final LoaderManager.LoaderCallbacks<T> callbacks, final LoaderManager loaderManager) {

        final Loader<T> loader = loaderManager.getLoader(loaderId);
        if (loader != null && loader.isReset()) {
            loaderManager.restartLoader(loaderId, args, callbacks);
        } else {
            loaderManager.initLoader(loaderId, args, callbacks);
        }
    }

    public static int countItemsNotEmpty(List<String> items) {
        if (items == null || items.size() == 0) {
            return 0;
        }

        int count = items.size();
        for (String item : items) {
            if (TextUtils.isEmpty(item)) {
                count--;
            }
        }
        return count;
    }

    /*
        Fragment supports
     */
    public static void replaceFragment(FragmentActivity context, String tag,
                                       Fragment f, @IdRes int containerId) {
        context.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        android.R.anim.fade_in, android.R.anim.fade_out,
                        android.R.anim.fade_in, android.R.anim.fade_out)
                .addToBackStack(tag)
                .replace(containerId, f).commit();
    }

    public static void replaceFragmentWithoutHistory(FragmentActivity context,
                                                     Fragment f, @IdRes int containerId) {
        context.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        android.R.anim.fade_in, android.R.anim.fade_out,
                        android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(containerId, f).commit();
    }

    public static void replaceFragment(FragmentActivity context,
                                       Fragment f, @IdRes int containerId, boolean addToBackStack) {
        FragmentTransaction ft = context.getSupportFragmentManager().beginTransaction();
        ft.replace(containerId, f);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
        context.getSupportFragmentManager().executePendingTransactions();
    }

    /**
     * @param activity
     * @param name     -> Sent null to pop top of stack.
     *                 Sent a valid tag to pop till that tag is reached.
     * @param flag     -> Either 0 or FragmentManager.POP_BACK_STACK_INCLUSIVE
     */
    public static void popBackStack(FragmentActivity activity, String name, int flag) {
        activity.getSupportFragmentManager().popBackStack(name, flag);
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

    /**
     * Give it 2 array of integers. It will return the smallest index in 'choices' that is not in the blacklist.
     * Returns -1 if no match is found.
     *
     * @param choices
     * @param blacklist
     * @return
     */
    public static int getFirstIntThatIsntInBlackList(ArrayList<Integer> choices, ArrayList<Integer> blacklist) {

        int chosenInt = 0;
        boolean idToAvoid = false;
        //Create a loop to go through all category id options returned by the article
        for (int i = 0; i < choices.size(); i++) {
            idToAvoid = false; //Reset flag since next id selected
            //Pick one of them
            chosenInt = choices.get(i);
            //Check if its inside the list to avoid
            for (int k = 0; k < blacklist.size(); k++) {
                //If it is, we throw a flag
                if (chosenInt == blacklist.get(k)) {
                    idToAvoid = true;
                    break;
                }
            }
            //If the flag is false, it means we found a valid id.
            //If flag is true, it means we have to loop again.
            if (!idToAvoid)
                break;
            else {
                //Reset for next loop. If all the categories this
                //article is in is within the list to avoid, then
                //we will chosenCategoryId will be 0
                chosenInt = -1;
            }
        }

        return chosenInt;
    }

    public static Object newInstance(Class clazz) {
        if (clazz == null) {
            return null;
        }

        Constructor[] ctors = clazz.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }

        try {
            ctor.setAccessible(true);
            return ctor.newInstance();

            // production code should handle these exceptions more gracefully
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
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

    public static boolean hasData(String... array) {
        if (array == null || array.length == 0) {
            return false;
        }

        for (String a : array) {
            if (!TextUtils.isEmpty(a)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasData(Collection<String> list) {
        if (list == null || list.size() == 0) {
            return false;
        }

        for (String item : list) {
            if (!TextUtils.isEmpty(item) && !"0".equals(item)) {
                return true;
            }
        }
        return false;
    }
}
