package com.example.wyyu.gitsamlpe.test.floatview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Created by wyyu on 2019/1/3.
 **/

public class ReceiverKeepLive extends BroadcastReceiver {

    @Override public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return;
        }
        if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            KeepLiveManager.getManager().startKeepLiveActivity();
        } else if (action.equals(Intent.ACTION_SCREEN_ON)) {
            KeepLiveManager.getManager().finishKeepLiveActivity();
        }
    }
}
