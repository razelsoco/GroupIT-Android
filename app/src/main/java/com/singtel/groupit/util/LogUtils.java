package com.singtel.groupit.util;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by lanna on 3/11/16.
 *
 */
public class LogUtils {

//    protected static boolean isDebug = BuildConfig.DEBUG;
    private static boolean isDebug = true;
    private static String DEFAULT_TAG = "app";

    // TODO Log model interface
    public interface ILogModel {
        String toLogString();
    }

    public static <T> void printArrayLog(String tag, List<T> models) {
        android.util.Log.i(tag, "[");
        for (T model : models) {
            android.util.Log.i(convertTag(tag), ""+models.indexOf(model)+": " + model);
        }
        android.util.Log.i(tag, "]");
    }

    // TODO Log(tag, msg)
    public static void d(String tag, String msg) {
        if (isDebug) {
            android.util.Log.d(convertTag(tag), getMessageNotNull(msg));
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            android.util.Log.w(convertTag(tag), getMessageNotNull(msg));
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            android.util.Log.i(convertTag(tag), getMessageNotNull(msg));
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            android.util.Log.v(convertTag(tag), getMessageNotNull(msg));
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            android.util.Log.e(convertTag(tag), getMessageNotNull(msg));
        }
    }

    // TODO Log(tag, msg, Throwable)
    public static void d(String tag, String msg, Throwable e) {
        if (isDebug) {
            android.util.Log.d(convertTag(tag), getMessageNotNull(msg), e);
        }
    }

    public static void w(String tag, String msg, Throwable e) {
        if (isDebug) {
            android.util.Log.w(convertTag(tag), getMessageNotNull(msg), e);
        }
    }

    public static void i(String tag, String msg, Throwable e) {
        if (isDebug) {
            android.util.Log.i(convertTag(tag), getMessageNotNull(msg), e);
        }
    }

    public static void v(String tag, String msg, Throwable e) {
        if (isDebug) {
            android.util.Log.v(convertTag(tag), getMessageNotNull(msg), e);
        }
    }

    public static void e(String tag, String msg, Throwable e) {
        if (isDebug) {
            android.util.Log.e(convertTag(tag), getMessageNotNull(msg), e);
        }
    }

    // Add methods for object
    // TODO Log(Object, msg)
    public static void e(Object o, String msg) {
        if (isDebug) {
            android.util.Log.e(convertTag(o.getClass().getSimpleName()), getMessageNotNull(msg));
        }
    }

    public static void d(Object o, String msg) {
        if (isDebug) {
            android.util.Log.d(convertTag(o.getClass().getSimpleName()), getMessageNotNull(msg));
        }
    }

    public static void w(Object o, String msg) {
        if (isDebug) {
            android.util.Log.w(convertTag(o.getClass().getSimpleName()), getMessageNotNull(msg));
        }
    }

    public static void i(Object o, String msg) {
        if (isDebug) {
            android.util.Log.i(convertTag(o.getClass().getSimpleName()), getMessageNotNull(msg));
        }
    }

    public static void v(Object o, String msg) {
        if (isDebug) {
            android.util.Log.v(convertTag(o.getClass().getSimpleName()), getMessageNotNull(msg));
        }
    }

    // TODO Log(Object, msg, Throwable)
    public static void e(Object o, String msg, Throwable e) {
        if (isDebug) {
            android.util.Log.e(convertTag(o.getClass().getSimpleName()), getMessageNotNull(msg), e);
        }
    }

    public static void d(Object o, String msg, Throwable e) {
        if (isDebug) {
            android.util.Log.d(convertTag(o.getClass().getSimpleName()), getMessageNotNull(msg), e);
        }
    }

    public static void w(Object o, String msg, Throwable e) {
        if (isDebug) {
            android.util.Log.w(convertTag(o.getClass().getSimpleName()), getMessageNotNull(msg), e);
        }
    }

    public static void i(Object o, String msg, Throwable e) {
        if (isDebug) {
            android.util.Log.i(convertTag(o.getClass().getSimpleName()), getMessageNotNull(msg), e);
        }
    }

    public static void v(Object o, String msg, Throwable e) {
        if (isDebug) {
            android.util.Log.v(convertTag(o.getClass().getSimpleName()), getMessageNotNull(msg), e);
        }
    }

    // TODO Log(func, tag, msg) (add 12/25/2012 by lanna)
    public static void d(String func, String tag, String msg) {
        if (isDebug) {
            android.util.Log.d(convertTag(func), tag+": "+getMessageNotNull(msg));
        }
    }

    public static void w(String func, String tag, String msg) {
        if (isDebug) {
            android.util.Log.w(convertTag(func), tag+": "+getMessageNotNull(msg));
        }
    }

    public static void i(String func, String tag, String msg) {
        if (isDebug) {
            android.util.Log.i(convertTag(func), tag+": "+getMessageNotNull(msg));
        }
    }

    public static void v(String func, String tag, String msg) {
        if (isDebug) {
            android.util.Log.v(convertTag(func), tag+": "+getMessageNotNull(msg));
        }
    }

    public static void e(String func, String tag, String msg) {
        if (isDebug) {
            android.util.Log.e(convertTag(func), tag+": "+getMessageNotNull(msg));
        }
    }

    // TODO Log(func, Object, msg) (add 12/25/2012 by lanna)
    public static void e(String func, Object o, String msg) {
        if (isDebug) {
            android.util.Log.e(convertTag(func), o.getClass().getSimpleName() + ": " + getMessageNotNull(msg));
        }
    }

    public static void d(String func, Object o, String msg) {
        if (isDebug) {
            android.util.Log.d(convertTag(func), o.getClass().getSimpleName() + ": " + getMessageNotNull(msg));
        }
    }

    public static void w(String func, Object o, String msg) {
        if (isDebug) {
            android.util.Log.w(convertTag(func), o.getClass().getSimpleName() + ": " + getMessageNotNull(msg));
        }
    }

    public static void i(String func, Object o, String msg) {
        if (isDebug) {
            android.util.Log.i(convertTag(func), o.getClass().getSimpleName() + ": " + getMessageNotNull(msg));
        }
    }

    public static void v(String func, Object o, String msg) {
        if (isDebug) {
            android.util.Log.v(convertTag(func), o.getClass().getSimpleName() + ": " + getMessageNotNull(msg));
        }
    }

    // TODO Log(msg) (add 11/29/2013 by lanna)
    public static void v(String msg) {
        v(DEFAULT_TAG, msg);
    }

    public static void d(String msg) {
        d(DEFAULT_TAG, msg);
    }

    public static void i(String msg) {
        i(DEFAULT_TAG, msg);
    }

    public static void w(String msg) {
        w(DEFAULT_TAG, msg);
    }

    public static void e(String msg) {
        e(DEFAULT_TAG, msg);
    }

    /*
        Other support
     */

    public static String convertTag(String tag) {
        tag = TextUtils.isEmpty(tag) ? DEFAULT_TAG
//                : !tag.contains(DEFAULT_TAG) ? (DEFAULT_TAG + "-" + tag)
                : tag;
        return tag.length() <= 23 ? tag : tag.substring(0, 23);
    }

    private static String getMessageNotNull(String msg) {
        if (msg == null) {
            msg = "null";
        }
        return msg;
    }

    /*
        Array
     */

    public static String toLogStrings(Object... logModels) {
        return toLogStrings(false, logModels);
    }

    public static String toLogStringsWithEnter(Object... logModels) {
        return toLogStrings(true, logModels);
    }

    private static String toLogStrings(boolean isEnter, Object... logModels) {
        if (logModels == null) {
            return "null";
        }

        Object model;
        int n = logModels.length - 1;
        StringBuilder log = new StringBuilder(logModels.length).append(isEnter ? "[\n" : "[");
        for (int i = 0; i <= n; i++) {
            model = logModels[i];
            if (model instanceof ILogModel) {
                log.append(((ILogModel) model).toLogString());
            } else {
                log.append(model.toString());
            }
            if (i != n) { // not last one
                log.append(isEnter ? ",\n" : ", ");
            }
        }
        log.append(isEnter ? "\n]" : "]");

        return log.toString();
    }

    /*
        List
     */


    public static String toLogStrings(List logModels) {
        return toLogStrings(false, logModels);
    }

    public static String toLogStringsWithEnter(List logModels) {
        return toLogStrings(true, logModels);
    }

    private static String toLogStrings(boolean isEnter, List logModels) {
        if (logModels == null) {
            return "null";
        }

        Object model;
        int n = logModels.size() - 1;
        StringBuilder log = new StringBuilder(logModels.size()).append(isEnter ? "[\n" : "[");
        for (int i = 0; i <= n; i++) {
            model = logModels.get(i);
            if (model instanceof ILogModel) {
                log.append(((ILogModel) model).toLogString());
            } else {
                log.append(model.toString());
            }
            if (i != n) { // not last one
                log.append(isEnter ? ",\n" : ", ");
            }
        }
        log.append(isEnter ? "\n]" : "]");

        return log.toString();
    }

}
