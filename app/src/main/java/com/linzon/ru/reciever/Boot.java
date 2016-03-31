package com.linzon.ru.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.linzon.ru.service.BasketService;

public class Boot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent service = new Intent(context, BasketService.class);
            service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(service);
        }
    }
}
