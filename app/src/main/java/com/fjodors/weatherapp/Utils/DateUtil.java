package com.fjodors.weatherapp.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * Format date so there would only year, month, day
     */
    public static String formatDate(Long dateMilliSeconds) {
        Date d = new Date(dateMilliSeconds);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(d);
    }

    /**
     * Format date so there would only hours and minutes
     */
    public static String formatTime(Long dateMilliSeconds) {
        Date d = new Date(dateMilliSeconds);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(d);
    }

    /**
     * Check if two dates are in the same day
     */
    public static boolean isSameDay(long currentWeatherDateSeconds,
                                    long previousWeatherDateSeconds) {
        //Need to transform from seconds to milliseconds for correct calendar operations
        Long dateMilliSeconds = TimeUtil.secondsToMilliSeconds(currentWeatherDateSeconds);
        Long dateMilliSecondsNext = TimeUtil.secondsToMilliSeconds(previousWeatherDateSeconds);

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(new Date(dateMilliSeconds));
        cal2.setTime(new Date(dateMilliSecondsNext));
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}
