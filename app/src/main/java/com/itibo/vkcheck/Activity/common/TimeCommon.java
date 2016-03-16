package com.itibo.vkcheck.Activity.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by erick on 15.10.15.
 */
public class TimeCommon {
    public static int getYearNumber(){
        return Calendar.getInstance(TimeZone.getDefault()).get(Calendar.YEAR);
    }

    public static int getMonthNumber(){
        return Calendar.getInstance(TimeZone.getDefault()).get(Calendar.MONTH) + 1;
    }

    public static int getDayNumber(){
        return Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DAY_OF_MONTH);
    }

    public static int getHourNumber(){
        return Calendar.getInstance(TimeZone.getDefault()).get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinuteNumber(){
        return Calendar.getInstance(TimeZone.getDefault()).get(Calendar.MINUTE);
    }

    public static int getSecondNumber(){
        return Calendar.getInstance(TimeZone.getDefault()).get(Calendar.SECOND);
    }

    public static int getHourFromUnix(int unix){
        return (int)(unix - (int)(Calendar.getInstance(TimeZone.getDefault()).getTimeInMillis()/1000 - getHourNumber()*3600 - getMinuteNumber()*60 - getSecondNumber()))/3600;
    }

    public static int getMinuteFromUnix(int unix){
        return (int)(unix - (int)(Calendar.getInstance(TimeZone.getDefault()).getTimeInMillis()/1000 - getHourNumber()*3600 - getMinuteNumber()*60 - getSecondNumber()))/3600;
    }

    public static int getUnixFromTime(int hour, int minute) {
        return (int)(System.currentTimeMillis() / 1000 - getHourNumber()*3600 - getMinuteNumber()*60 - getSecondNumber() + hour * 3600 + minute * 60);
    }

    public static String getTimeFromUnix(int unix) {
        Calendar time = Calendar.getInstance(TimeZone.getDefault());
        time.setTimeInMillis(unix * 1000L);
        String sunrise = new SimpleDateFormat("HH:mm:ss").format(time.getTime());
        if(unix > Calendar.getInstance(TimeZone.getDefault()).getTimeInMillis()/1000 - getHourNumber()*3600 - getMinuteNumber()*60 - getSecondNumber()) {
            return "Сегодня в " + sunrise;
        }
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(time.getTime());
    }

    public static String getSimpleTimeFromUnix(int unix) {
        Calendar time = Calendar.getInstance(TimeZone.getDefault());
        time.setTimeInMillis(unix * 1000L);
        String sunrise = new SimpleDateFormat("HH:mm:ss").format(time.getTime());
        if(unix > Calendar.getInstance(TimeZone.getDefault()).getTimeInMillis()/1000 - getHourNumber()*3600 - getMinuteNumber()*60 - getSecondNumber()) {
            return sunrise;
        }
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(time.getTime());
    }

    public static String getHourMinuteFromUnix(int time) {
        int hour = time / 3600;
        String hourString = String.valueOf(hour).length() == 1 ? "0" + hour + ":" : hour + ":";
        int minute = (time - hour * 3600)/60;
        String minuteString = String.valueOf(minute).length() == 1 ? "0" + minute + ":" : minute + ":";
        int second = (time - hour * 3600 - minute * 60);
        String secondString = String.valueOf(second).length() == 1 ? "0" + second : String.valueOf(second);
        return (hour == 0 ? "" : hourString) + minuteString + secondString;
    }

    public static int[] getArrayHourMinute(int time) {
        int hour = time / 60;
        int minute = (time - hour * 60)/60;
        int[] result = new int[2];
        result[0] = hour;
        result[1] = minute;
        return result;
    }
}
