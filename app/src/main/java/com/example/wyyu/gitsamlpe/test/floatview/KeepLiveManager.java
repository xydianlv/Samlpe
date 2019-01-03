package com.example.wyyu.gitsamlpe.test.floatview;

import android.content.Intent;
import android.content.IntentFilter;
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
}
