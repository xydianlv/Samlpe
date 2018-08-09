package com.example.wyyu.gitsamlpe.util.notify;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.example.wyyu.gitsamlpe.framework.application.AppController;

/**
 * Created by wyyu on 2018/5/28.
 **/

public class NotifyManager {

    public enum ChannelValue {

        通知("notify", 1001);

        public String channelName;
        public int channelId;

        ChannelValue(String channelName, int channelId) {
            this.channelName = channelName;
            this.channelId = channelId;
        }

        public int getImportance() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                return 0;
            }
            switch (channelId) {
                case 1001:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        return NotificationManager.IMPORTANCE_DEFAULT;
                    }
                default:
                    return NotificationManager.IMPORTANCE_DEFAULT;
            }
        }
    }

    private static class NotifyManagerHolder {
        private static NotifyManager manager = new NotifyManager();
    }

    public static NotifyManager getManager() {
        return NotifyManagerHolder.manager;
    }

    private NotificationManager notificationManager;

    private NotifyManager() {
        initNotificationManager();
    }

    private void initNotificationManager() {
        notificationManager = (NotificationManager) AppController.getAppContext()
            .getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * 发送通知栏消息
     *
     * @param channelValue 该条消息所属消息通道
     * @param title 消息标题
     * @param text 消息内容
     * @param smallIcon 小图标
     * @param bigIcon 大图标
     * @param intent 点击状态栏消息跳转
     */
    public void sendNotify(ChannelValue channelValue, String title, String text, int smallIcon,
        int bigIcon, PendingIntent intent) {

        if (channelValue == null || smallIcon == 0) {
            return;
        }
        if (notificationManager == null) {
            initNotificationManager();
        }
        Context context = AppController.getAppContext();

        NotificationCompat.Builder builder =
            new NotificationCompat.Builder(context, channelValue.channelName);

        builder.setContentTitle(TextUtils.isEmpty(title) ? "" : title);
        builder.setContentText(TextUtils.isEmpty(text) ? "" : text);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setSmallIcon(smallIcon);

        if (bigIcon != 0) {
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), bigIcon));
        }
        if (intent != null) {
            builder.setContentIntent(intent);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channelSection =
                new NotificationChannel(channelValue.channelName, channelValue.channelName,
                    channelValue.getImportance());
            notificationManager.createNotificationChannel(channelSection);
        }

        notificationManager.notify(channelValue.channelId, builder.build());
    }
}
