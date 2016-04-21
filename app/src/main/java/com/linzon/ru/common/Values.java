package com.linzon.ru.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.linzon.ru.Basket;
import com.linzon.ru.models.CustomOfferData;

import java.util.Calendar;
import java.util.TimeZone;

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
        return (unix - (int)(System.currentTimeMillis() / 1000 - Values.getHourNumber()*3600 - Values.getMinuteNumber()*60 - Values.getSecondNumber())) /3600;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void showTopSnackBar(Activity activity, String text, String buttonText, View.OnClickListener onClickListener, int howLong) {
        Snackbar snack = Snackbar.make(activity.findViewById(android.R.id.content), text, howLong);
        if(onClickListener != null) {
            snack.setAction(buttonText, onClickListener);
        }
        View view = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        view.setLayoutParams(params);
        snack.show();
    }

    public static String[] combine(String[] a, String[] b){
        int length = a.length + b.length;
        String[] result = new String[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public static String createCustomJsonData(Object TYPE, Object BC, Object PWR, Object AX, Object CYL, Object COLOR) {
        CustomOfferData data = new CustomOfferData(
                TYPE != null ? TYPE.toString() : null,
                BC != null ? BC.toString() : null,
                PWR != null ? PWR.toString() : null,
                AX != null ? AX.toString() : null,
                CYL != null ? CYL.toString() : null,
                COLOR != null ? COLOR.toString() : null
        );
        return data.toString();
    }

    public static Double[] stringToDouble(String[] array) {
        Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Double.parseDouble(array[i].substring(1, array[i].length()));
        }

        return result;
    }

    public static String[] doubleToString(Double[] array) {
        String[] s = new String[array.length];

        for (int i = 0; i < s.length; i++)
            s[i] = array[i] > 0 ? "+" + String.valueOf(array[i]) : String.valueOf(array[i]);

        return s;
    }
}
