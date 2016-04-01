package com.linzon.ru.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedProperty {
    private static SharedProperty instance;
    private static Context context;

    public static void init(Context context) {
        SharedProperty.context = context;
    }

    public static synchronized SharedProperty getInstance() {
        if(null == instance) {
            instance = new SharedProperty();
        }
        return instance;
    }

    public String getValue(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    public void setValue(String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static final String APP_VERSION = "app_version";
    public static final String USER_NAME = "username";
    public static final String USER_EMAIL = "email";
    public static final String USER_PHONE = "phone";
}
