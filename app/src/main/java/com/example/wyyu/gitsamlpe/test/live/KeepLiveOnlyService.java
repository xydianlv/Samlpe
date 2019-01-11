package com.example.wyyu.gitsamlpe.test.live;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2019/1/11.
 **/

public class KeepLiveOnlyService extends Service {

    private static final String NOTIFICATION_NAME = "channelName";
    private static final String NOTIFICATION_ID = "channelId";

    @Nullable @Override public IBinder onBind(Intent intent) {
        return null;
    }

    // 将后台服务设置为前台
    @Override public void onCreate() {
        super.onCreate();

        NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager == null) {
            return;
        }

        //创建NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                new NotificationChannel(NOTIFICATION_ID, NOTIFICATION_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        startForeground(0, getNotification());
    }

    private Notification getNotification() {
        Notification.Builder builder =
            new Notification.Builder(this).setSmallIcon(R.drawable.ic_empty_zhihu)
                .setContentTitle("")
                .setContentText("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(NOTIFICATION_ID);
        }

        return builder.build();
    }
}
