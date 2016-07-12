package com.singtel.groupit.model.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class BaseSharedPreferences {
//	public static final String DEFAULT_LANGUAGE = Locale.ENGLISH.getLanguage();
    private static final String PREF_NAME = "com.singtel.groupit.PREF";
	public static final String DEFAULT_STRING = "";
	public static final boolean DEFAULT_BOOLEAN = false;
	public static final int DEFAULT_NUMBER = 0;

    protected Context context;
    protected SharedPreferences pref;
    public BaseSharedPreferences(Context context) {
        this.pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.context = context;
    }

	// get, set string
    protected String getString(String key) {
        return getString(key, DEFAULT_STRING);
    }

    protected String getString(String key, String backup) {
        return pref.getString(key, backup);
    }

	protected void submitString(String key, String value) {
        pref.edit().putString(key, value).apply();
	}

    protected SharedPreferences.Editor putString(String key, String value) {
        return pref.edit().putString(key, value);
    }


	// get, set integer
    protected int getInt(String key) {
        return getInt(key, DEFAULT_NUMBER);
    }

    protected int getInt(String key, int backup) {
        return pref.getInt(key, backup);
    }

    protected void submitInt(String key, int value) {
        pref.edit().putInt(key, value).apply();
	}

    protected SharedPreferences.Editor putInt(String key, int value) {
        return pref.edit().putInt(key, value);
    }


	// get, set long
    protected long getLong(String key) {
        return getLong(key, DEFAULT_NUMBER);
    }

    protected long getLong(String key, long backup) {
        return pref.getLong(key, backup);
    }

	protected void submitLong(String key, long value) {
        pref.edit().putLong(key, value).apply();
	}

    protected SharedPreferences.Editor putLong(String key, long value) {
        return pref.edit().putLong(key, value);
    }


    // get, set float
    protected float getFloat(String key) {
        return getFloat(key, DEFAULT_NUMBER);
    }

    protected float getFloat(String key, float backup) {
        return pref.getFloat(key, backup);
    }

    protected void submitFloat(String key, float value) {
        pref.edit().putFloat(key, value).apply();
	}

    protected SharedPreferences.Editor putFloat(String key, float value) {
        return pref.edit().putFloat(key, value);
    }


    // get, set boolean
    protected boolean getBoolean(String key) {
        return getBoolean(key, DEFAULT_BOOLEAN);
    }

    protected boolean getBoolean(String key, boolean backup) {
        return pref.getBoolean(key, backup);
    }

	protected void submitBoolean(String key, boolean value) {
        pref.edit().putBoolean(key, value).apply();
	}

    protected SharedPreferences.Editor putBoolean(String key, boolean value) {
        return pref.edit().putBoolean(key, value);
    }

}
