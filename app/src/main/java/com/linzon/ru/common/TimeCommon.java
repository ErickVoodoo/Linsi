package com.linzon.ru.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

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

    public static int getUnixTime() {
        return (int) (System.currentTimeMillis() / 1000L);
    }

    public static int getHourFromUnix(int unix){
        return (unix - (int)(Calendar.getInstance(TimeZone.getDefault()).getTimeInMillis()/1000 - getHourNumber()*3600 - getMinuteNumber()*60 - getSecondNumber())) /3600;
    }

    public static int getMinuteFromUnix(int unix){
        return (unix - (int)(Calendar.getInstance(TimeZone.getDefault()).getTimeInMillis()/1000 - getHourNumber()*3600 - getMinuteNumber()*60 - getSecondNumber())) /3600;
    }

    public static int getUnixFromTime(int hour, int minute) {
        return (int)(System.currentTimeMillis() / 1000 - getHourNumber()*3600 - getMinuteNumber()*60 - getSecondNumber() + hour * 3600 + minute * 60);
    }
}
