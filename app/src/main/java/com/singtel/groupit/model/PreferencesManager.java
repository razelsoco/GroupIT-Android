package com.singtel.groupit.model;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by razelsoco on 12/7/16.
 */

public class PreferencesManager {

    private static final String PREF_NAME = "com.singtel.groupit.PREFERENCES";
    private static final String KEY_AUTH_TOKEN = "com.singtel.groupit.AUTH_TOKEN";

    private static PreferencesManager sInstance;
    private final SharedPreferences mPref;

    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
    }

    public static synchronized PreferencesManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(PreferencesManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    public void setAuthToken(String value) {
        mPref.edit()
                .putString(KEY_AUTH_TOKEN, "Bearer "+value)
                .commit();
    }

    public String getAuthToken() {
        return mPref.getString(KEY_AUTH_TOKEN, null);
    }

    public void remove(String key) {
        mPref.edit()
                .remove(key)
                .commit();
    }

    public boolean clear() {
        return mPref.edit()
                .clear()
                .commit();
    }
}