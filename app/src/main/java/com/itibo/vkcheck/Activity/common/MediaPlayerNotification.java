package com.itibo.vkcheck.Activity.common;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.itibo.vkcheck.Activity.MainActivity;
import com.itibo.vkcheck.R;

/**
 * Created by erick on 24.11.15.
 */
public class MediaPlayerNotification {
    public void createNotificationPlayer(Context context, String artist, String title) {
        RemoteViews view = new RemoteViews(getClass().getPackage().getName(), R.layout.notification_layer);

        String strartist = artist;
        String strtitle = title;

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("title", strtitle);
        intent.putExtra("artist", strartist);

        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.app_launcher)
                .setTicker(strartist + " " + strtitle)
                .setAutoCancel(true)
                .setContentIntent(pIntent)
                .setContent(view);

        view.setTextViewText(R.id.notificationArtist, strartist);
        view.setTextViewText(R.id.notificationTitle, strtitle);

        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationmanager.notify(0, builder.build());
    }
}
