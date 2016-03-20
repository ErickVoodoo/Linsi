package com.linzon.ru;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.linzon.ru.api.ApiConnector;
import com.linzon.ru.common.Constants;
import com.linzon.ru.database.DBHelper;
import com.linzon.ru.service.NotificationService;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBHelper.init(getApplicationContext());
        if(DBHelper.getInstance().selectRows(DBHelper.OFFERS,null , null, null, null).getCount() == 0) {
            ApiConnector.asyncGetOfferList(Constants.STATIC_APP, new ApiConnector.CallbackGetOfferList() {
                @Override
                public void onSuccess(String success) {

                }

                @Override
                public void onError(String error) {
                    Log.e("NETWORK", "ERROR");
                }
            });
        }
        //DBHelper.getInstance().dropDatabase();
        /*Intent service = new Intent(this, NotificationService.class);
        service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startService(service);*/
    }
}
