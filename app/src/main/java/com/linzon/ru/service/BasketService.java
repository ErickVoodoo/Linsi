package com.linzon.ru.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.linzon.ru.common.Constants;
import com.linzon.ru.database.DBHelper;

public class BasketService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constants.BROADCAST_REMOVE_OFFER: {

                    break;
                }
                case Constants.BROADCAST_UPDATE_COUNT: {
                    String id = intent.getStringExtra("ID");
                    String count = intent.getStringExtra("COUNT");
                    DBHelper.updateBasketCount(id, count);
                    Intent updatePrice = new Intent();
                    updatePrice.setAction(Constants.BROADCAST_UPDATE_PRICE);
                    LocalBroadcastManager.getInstance(BasketService.this).sendBroadcast(updatePrice);
                }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_REMOVE_OFFER);
        intentFilter.addAction(Constants.BROADCAST_UPDATE_COUNT);
        this.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        this.unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
