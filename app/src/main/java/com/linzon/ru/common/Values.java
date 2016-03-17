package com.linzon.ru.common;

import android.content.Context;
import android.util.DisplayMetrics;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by erick on 16.10.15.
 */
public class Values {
    public static int GET_SCREEN_WIDTH(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static int GET_SCREEN_HEIGHT(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int)((dp * displayMetrics.density));
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
        return (int)(unix - (int)(System.currentTimeMillis() / 1000 - Values.getHourNumber()*3600 - Values.getMinuteNumber()*60 - Values.getSecondNumber()))/3600;
    }

    public static final String SERVICE_START_MUSIC = "service.start_music";
    public static final String SERVICE_STOP_MUSIC = "service.stop_music";
    public static final String SERVICE_PAUSE_MUSIC = "service.pause_music";

    public static final String USER_GET_URL = "userprofile.get_url";
    public static final String USER_GET_ISPLAYING = "userprofile.isplaying";
    public static final String SERVICE_GET_URL = "service.get_url";
    public static final String SERVICE_GET_ISPLAYING = "service.isplaying";
}
