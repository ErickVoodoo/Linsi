package com.linzon.ru.Activity.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.linzon.ru.Activity.api.Vk;
import com.linzon.ru.Activity.common.TimeCommon;
import com.linzon.ru.Activity.database.DBHelper;
import com.linzon.ru.Activity.models.MainOffer;
import com.linzon.ru.Activity.models.SearchModel;
import com.linzon.ru.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by erick on 28.10.15.
 */
public class NotificationService extends Service {
    private static final long SYNC_NOTIFY_INTERVAL = 300000;

    int count = 0;

    Timer notificationTimer;

    private Runnable runnable;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("onCreate()", "NotificationService");
        runnable = new Runnable() {
            @Override
            public void run() {
                Log.e("CHECK", "RUNNING");
                Vk.asyncGetUsersShort(DBHelper.getStringUsers("notification='1'"), new Vk.CallbackArraySearchModel() {
                    @Override
                    public void onSuccess(final ArrayList<SearchModel> model) {
                        if(model != null)
                        for (int i = 0; i < model.size(); i++) {
                            final MainOffer user = DBHelper.getUserFromDatabase(String.valueOf(model.get(i).getUid()));
                            if (user != null) {
                                if (model.get(i).getOnline() != user.getOnline()) {
                                    final int finalI = i;
                                    new AsyncTask<Integer, Void, Bitmap>() {
                                        @Override
                                        protected Bitmap doInBackground(Integer... params) {
                                            Bitmap btm = null;
                                            try {

                                                btm = Picasso
                                                        .with(NotificationService.this)
                                                        .load(model.get(finalI).getPhoto_100())
                                                        .resize(64, 64)
                                                        .get();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                            return btm;
                                        }

                                        @Override
                                        protected void onPostExecute(Bitmap result) {
                                            NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationService.this.getApplicationContext())
                                                    .setAutoCancel(true)
                                                    .setColor(getResources().getColor(R.color.colorPrimary))
                                                    .setContentTitle(model.get(finalI).getFirst_name() + " " + model.get(finalI).getLast_name());

                                            if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
                                                builder.setSmallIcon(R.drawable.app_launcher);
                                            } else {
                                                builder.setSmallIcon(R.drawable.app_launcher);
                                            }

                                            builder .setTicker(model.get(finalI).getFirst_name() + " " + model.get(finalI).getLast_name() + (model.get(finalI).getOnline() == 1 ? " онлайн " : " оффлайн "))
                                                    .setLargeIcon(result)
                                                    .setContentText("Пользователь " + (model.get(finalI).getOnline() == 1 ? " онлайн " : " оффлайн ") + "с " + TimeCommon.getSimpleTimeFromUnix(model.get(finalI).getLast_seen().getTime()));

                                            Notification notification = builder.build();
                                            NotificationManager notificationManager = (NotificationManager) NotificationService.this.getSystemService(Context.NOTIFICATION_SERVICE);
                                            notificationManager.notify(count, notification);

                                            DBHelper.getInstance().updateRows(DBHelper.USERS, DBHelper.setUserContentValues(user.getUid(), user.getNotification(), model.get(finalI).getOnline(), model.get(finalI).getLast_seen().getTime()), "uid='" + user.getUid() + "'");
                                            count++;
                                        }

                                    }.execute(i);
                                    Log.e("USER", model.get(i).getOnline() + "|" + user.getOnline() + "|" + user.getUid());
                                    Log.e(model.get(i).getFirst_name() + " " + model.get(i).getLast_name(), "Пользователь" + (model.get(i).getOnline() == 0 ? " онлайн " : " оффлайн ") + "с " + TimeCommon.getSimpleTimeFromUnix(model.get(i).getLast_seen().getTime()));
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        };

        notificationTimer = new Timer();
        notificationTimer.scheduleAtFixedRate(new NotificationTimerSchedule(), 0, SYNC_NOTIFY_INTERVAL);
    }

    public class NotificationTimerSchedule extends TimerTask {
        @Override
        public void run() {
            new Thread(runnable).start();
        }
    }

    @Override
    public void onDestroy() {
        Log.e("onDestroy()", "NotificationService");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
