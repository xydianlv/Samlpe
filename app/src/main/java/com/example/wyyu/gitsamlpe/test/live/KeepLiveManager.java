package com.example.wyyu.gitsamlpe.test.live;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.application.AppController;

/**
 * Created by wyyu on 2019/1/8.
 **/

public class KeepLiveManager implements IKeepLiveManager {

    private static class ManagerHolder {
        private static final KeepLiveManager manager = new KeepLiveManager();
    }

    public static KeepLiveManager getManager() {
        return ManagerHolder.manager;
    }

    private static final String CHANNEL_NAME = "notify";
    private static final int CHANNEL_ID = 1001;

    private NotificationManager notificationManager;
    private KeepLiveReceiver keepLiveReceiver;

    private KeepLiveManager() {
        keepLiveReceiver = new KeepLiveReceiver();
        initNotificationManager();
    }

    private void initNotificationManager() {
        notificationManager = (NotificationManager) AppController.getAppContext()
            .getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override public void init() {

    }

    @Override public void startBackService() {
        Context context = AppController.getAppContext();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, KeepLiveOnlyService.class));
        } else {
            context.startService(new Intent(context, KeepLiveOnlyService.class));
        }
    }

    @Override public void closeBackService() {

    }

    @Override public void showFloatView(Context context) {
        if (!FloatPermissionManager.getInstance().applyFloatWindow(context)) {
            return;
        }
        ContextCompat.startForegroundService(context, new Intent(context, KeepLiveOnlyFloat.class));
    }

    @Override public void hideFloatView() {

    }

    @Override public void openCloseListener() {
        if (keepLiveReceiver == null) {
            keepLiveReceiver = new KeepLiveReceiver();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);

        AppController.getAppContext().registerReceiver(keepLiveReceiver, intentFilter);
    }

    @Override public void closeCloseListener() {
        if (keepLiveReceiver == null) {
            return;
        }
        AppController.getAppContext().unregisterReceiver(keepLiveReceiver);
    }

    @Override public void showNotifyBar() {
        if (notificationManager == null) {
            initNotificationManager();
        }

        Context context = AppController.getAppContext();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_NAME);

        builder.setContentTitle("");
        builder.setContentText("");
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setSmallIcon(R.mipmap.arrow_right);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channelSection = new NotificationChannel(CHANNEL_NAME, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channelSection);
        }

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        notificationManager.notify(CHANNEL_ID, notification);
    }

    @Override public void hideNotifyBar() {

    }
}
