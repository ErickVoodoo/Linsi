package com.itibo.vkcheck;

import android.app.Application;
import android.content.Intent;

import com.itibo.vkcheck.Activity.database.DBHelper;
import com.itibo.vkcheck.Activity.service.MusicPlayer;
import com.itibo.vkcheck.Activity.service.NotificationService;

/**
 * Created by erick on 21.10.15.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DBHelper.init(getApplicationContext());

        Intent service = new Intent(this, NotificationService.class);
        service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startService(service);
    }
}
