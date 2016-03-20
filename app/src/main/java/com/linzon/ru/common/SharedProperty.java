package com.linzon.ru.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedProperty {
    private static SharedProperty instance;
    private static Activity activity;

    public static void init(Activity activity) {
        SharedProperty.activity = activity;
    }

    public static synchronized SharedProperty getInstance() {
        if(null == instance) {
            instance = new SharedProperty();
        }
        return instance;
    }

    public boolean isCurrentName() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("user", Context.MODE_PRIVATE);
        return (! sharedPreferences.getString("username", "").equals(""));
    }

    public String getCurrentName() {
        if(activity == null) {
            return "noName";
        }
        SharedPreferences sharedPreferences = activity.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", "");
    }

    public void setCurrentName(String username) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();
    }
}
