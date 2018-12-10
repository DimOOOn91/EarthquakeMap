package com.dmytro.lisovyi.earthquakemap.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateTimeUtils {

    public static final int DAY = 24 * 60 * 60 * 1000;

    private static String REQUEST_DATE_FORMAT = "yyyy-MM-dd";
    private static String TIME_FORMAT = "HH:mm";

    public static String getFormattedTime(long millis) {
        Date date = new Date(millis);
        return new SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(date);
    }

    public static String getFormattedDate(long millis) {
        Date date = new Date(millis);
        return new SimpleDateFormat(REQUEST_DATE_FORMAT, Locale.getDefault()).format(date);
    }

    public static long getBeginOfCurrentDay(){
        return getBeginOfDay(System.currentTimeMillis());
    }

    public static long getBeginOfNextDay() {
        return getBeginOfDay(System.currentTimeMillis() + DAY);
    }

    private static long getBeginOfDay(long time){
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

}
