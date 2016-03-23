package com.linzon.ru;

import android.app.Application;
import android.content.Intent;

import com.linzon.ru.common.SharedProperty;
import com.linzon.ru.database.DBHelper;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBHelper.init(getApplicationContext());
        SharedProperty.init(getApplicationContext());

        if(DBHelper.getInstance().selectRows(DBHelper.OFFERS,null , null, null, null).getCount() == 0) {
            Intent intent = new Intent(this, StartScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            if(false) {
                /*TODO CHECK IF NEED UPDATE FROM SERVER*/
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
        //DBHelper.getInstance().dropDatabase();
        /*Intent service = new Intent(this, NotificationService.class);
        service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startService(service);*/
    }
}
