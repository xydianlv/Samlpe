package com.example.wyyu.gitsamlpe.util.notify;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/5/28.
 **/

public class NotifyManager {

    private static class NotifyManagerHolder {
        private static NotifyManager manager = new NotifyManager();
    }

    public static NotifyManager getManager() {
        return NotifyManagerHolder.manager;
    }

    private NotifyManager() {

    }

    public void sendNotify(@NonNull Context context) {

        int smallIcon = Build.VERSION.SDK_INT < 21 ? R.mipmap.audio_pause : R.mipmap.audio_play;
        String appName = "测试";

        NotificationCompat.Builder notifyBuilder =
            new NotificationCompat.Builder(context, "私信").setContentTitle(appName)
                .setContentText("树洞消息")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(smallIcon)
                .setAutoCancel(true)
                .setDefaults(0);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);

        if (manager.areNotificationsEnabled()) {
            manager.notify(10010, notifyBuilder.build());
        }
    }
}
