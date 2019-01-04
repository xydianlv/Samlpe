package com.example.wyyu.gitsamlpe.test.floatview;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import com.example.wyyu.gitsamlpe.framework.message.MsgSender;
import com.example.wyyu.gitsamlpe.framework.message.MsgType;

/**
 * Created by wyyu on 2019/1/3.
 **/

public class KeepLiveManager implements IKeepLiveManager {

    private static class ManagerHolder {
        private static final KeepLiveManager manager = new KeepLiveManager();
    }

    public static KeepLiveManager getManager() {
        return ManagerHolder.manager;
    }

    private static final int FOREGROUND_PUSH_ID = 1;

    private ReceiverKeepLive receiverKeepLive;

    private KeepLiveManager() {
        receiverKeepLive = new ReceiverKeepLive();
    }

    @Override public void init() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);

        AppController.getAppContext().registerReceiver(receiverKeepLive, intentFilter);
    }

    @Override public void startKeepLiveActivity() {
        ActivityKeepLive.open(AppController.getAppContext());
    }

    @Override public void finishKeepLiveActivity() {
        MsgSender.getMsgSender().sendMessage(MsgType.FINISH_KEEP_LIVE, null);
    }

    @Override public void setForeground(Service keepLiveService, Service innerService) {
        if (keepLiveService == null) {
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            keepLiveService.startForeground(FOREGROUND_PUSH_ID, new Notification());
        } else {
            keepLiveService.startForeground(FOREGROUND_PUSH_ID, new Notification());
            if (innerService != null) {
                innerService.startForeground(FOREGROUND_PUSH_ID, new Notification());
                innerService.stopSelf();
            }
        }
    }
}
