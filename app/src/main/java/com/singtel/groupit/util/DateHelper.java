package com.singtel.groupit.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.text.format.DateUtils;

import com.singtel.groupit.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateHelper {

    public static final String COMMON_SERVER_DATE_ONLY_FORMAT = "EEE, dd MMM yyyy";
    public static final String COMMON_SERVER_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z"; // format 24h
    public static final String COMMON_SERVER_SHORT_DATE_FORMAT = "dd-MM-yyyy"; // format 24h


    // base date methods
    public static String dateMillisToString(long timeStamp, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        return sdf.format(new Date(timeStamp));
    }

    public static long dateStringToMillis(String time, String format) {
        if (TextUtils.isEmpty(time)) {
            return -1;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        try {
            return sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // full date
    public static long dateStringToMillis(String time) {
        return dateStringToMillis(time, COMMON_SERVER_DATE_FORMAT);
    }

    public static String dateMillisToString(long timeStamp) {
        return dateMillisToString(timeStamp, COMMON_SERVER_DATE_FORMAT);
    }

    // short date
    public static long dateOnlyInMillis(String date) {
        return dateStringToMillis(date, COMMON_SERVER_DATE_ONLY_FORMAT);
    }

    public static String dateOnlyToString(long timestamp) {
        return dateMillisToString(timestamp, COMMON_SERVER_DATE_ONLY_FORMAT);
    }

    /*****************************************************************
     * Function to return a string representation of the day
     ****************************************************************/
    public static String dayOfWeekToString(Context context, int dayOfWeek) {
        switch (dayOfWeek) {
            case 1:
                return context.getString(R.string.sunday);
            case 2:
                return context.getString(R.string.monday);
            case 3:
                return context.getString(R.string.tuesday);
            case 4:
                return context.getString(R.string.wednesday);
            case 5:
                return context.getString(R.string.thursday);
            case 6:
                return context.getString(R.string.friday);
            case 7:
                return context.getString(R.string.saturday);
        }
        return "";
    }


    /*****************************************************************
     * Function to return relevant time, i.e. "2 days ago", "now"
     ****************************************************************/
    public static String timeRecentlyElapsed(Context context, long timeStamp) {
        if (timeStamp < 0) {
            return "";
        }

        long relevantTime = System.currentTimeMillis() - timeStamp;
        if (relevantTime < 0) {
            return "";
        }

        int DAYS_IN_WEEK = 7; // only within 24 hours.
        int MAX_WEEKS_TO_SHOW_RELAVENT_DATE = 4; // zero means show xx hours ago,

        long remainder = (relevantTime / DateUtils.DAY_IN_MILLIS);

        // more than max weeks
        if (remainder > MAX_WEEKS_TO_SHOW_RELAVENT_DATE * DAYS_IN_WEEK) {
            return dateMillisToString(timeStamp * 1000, COMMON_SERVER_DATE_ONLY_FORMAT);
        }

        // in weeks
        if (remainder >= DAYS_IN_WEEK) {
            int weeknumber = (int) remainder / DAYS_IN_WEEK;
            if (weeknumber >= 1)
                return getOneOrManyStringRes(context, weeknumber, R.string.week_ago, R.string.weeks_ago);
        }

        // in days
        if (remainder >= 1)
            return getOneOrManyStringRes(context, remainder, R.string.day_ago, R.string.days_ago);

        // in hours
        remainder = relevantTime / DateUtils.HOUR_IN_MILLIS;
        if (remainder >= 1)
            return getOneOrManyStringRes(context, remainder, R.string.hour_ago, R.string.hours_ago);

        // in minutes
        remainder = relevantTime / DateUtils.MINUTE_IN_MILLIS;
        if (remainder >= 1)
            return getOneOrManyStringRes(context, remainder, R.string.minute_ago, R.string.minutes_ago);

        // seconds / just now / recently / ...
        return context.getString(R.string.now);
    }

    public static String timeRecentlyElapsed(Context context, String timeStamp) {
        return timeRecentlyElapsed(context, dateStringToMillis(timeStamp));
    }

    public static String getOneOrManyStringRes(Context context, long number,
                                               @StringRes int oneString, @StringRes int manyString) {
        return number + ((number <= 1) ? context.getString(oneString) : context.getString(manyString));
    }
/*
    public static boolean sameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        return sameDay;
    }

    public static boolean beforeDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        boolean beforeDay = cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)
                || (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)&&
                    cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR)
        );
//        LogUtils.i("datetime", "beforeDay: " + beforeDay
//                + " by day1: "+cal1.get(Calendar.DAY_OF_YEAR)+"/"+cal1.get(Calendar.YEAR)
//                + " vs day2: "+cal2.get(Calendar.DAY_OF_YEAR)+"/"+cal2.get(Calendar.YEAR));
        return beforeDay;
    }

    public static boolean afterDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        boolean afterDay = cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)
                || (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)&&
                cal1.get(Calendar.DAY_OF_YEAR) > cal2.get(Calendar.DAY_OF_YEAR)
        );
//        LogUtils.i("datetime", "afterDay: " + afterDay
//                + " by day1: "+cal1.get(Calendar.DAY_OF_YEAR)+"/"+cal1.get(Calendar.YEAR)
//                + " vs day2: "+cal2.get(Calendar.DAY_OF_YEAR)+"/"+cal2.get(Calendar.YEAR));
        return afterDay;
    }
    //*/
}