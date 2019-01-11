package com.example.wyyu.gitsamlpe.test.live;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.example.wyyu.gitsamlpe.framework.application.AppController;

/**
 * Created by wyyu on 2019/1/8.
 **/

public class KeepLiveReceiver extends BroadcastReceiver {

    @Override public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return;
        }
        if (action.equals(Intent.ACTION_SCREEN_ON)) {
            AppController.getAppContext()
                .sendBroadcast(new Intent(FinishPixelReceiver.ACTION_FINISH_PIXEL));
        } else {
            KeepLiveActivity.open(AppController.getAppContext());
        }
    }
}
