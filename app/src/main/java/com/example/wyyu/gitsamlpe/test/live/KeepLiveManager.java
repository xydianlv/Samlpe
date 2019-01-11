package com.example.wyyu.gitsamlpe.test.live;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

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

    private KeepLiveReceiver keepLiveReceiver;
    private Context context;

    private boolean hasInit;

    private KeepLiveManager() {
        keepLiveReceiver = new KeepLiveReceiver();
        hasInit = false;
    }

    @Override public void init(Context context) {
        if (context == null || hasInit) {
            return;
        }
        if (keepLiveReceiver == null) {
            keepLiveReceiver = new KeepLiveReceiver();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);

        context.registerReceiver(keepLiveReceiver, intentFilter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, KeepLiveService.class));
        } else {
            context.startService(new Intent(context, KeepLiveService.class));
        }

        this.context = context;
        this.hasInit = true;
    }

    @Override public void startPixelActivity() {
        if (context == null) {
            return;
        }
        KeepLiveActivity.open(context);
    }

    @Override public void finishPixelActivity() {
        Intent intent = new Intent(FinishPixelReceiver.ACTION_FINISH_PIXEL);
        context.sendBroadcast(intent);
    }
}
