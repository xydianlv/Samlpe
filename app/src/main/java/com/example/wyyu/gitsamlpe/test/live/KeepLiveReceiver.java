package com.example.wyyu.gitsamlpe.test.live;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Created by wyyu on 2019/1/8.
 **/

public class KeepLiveReceiver extends BroadcastReceiver {

    @Override public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return;
        }
        if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            KeepLiveManager.getManager().startPixelActivity();
        } else if (action.equals(Intent.ACTION_SCREEN_ON)) {
            KeepLiveManager.getManager().finishPixelActivity();
        }
    }
}
