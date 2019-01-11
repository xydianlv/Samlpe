package com.example.wyyu.gitsamlpe.test.live;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Created by wyyu on 2019/1/8.
 **/

public class FinishPixelReceiver extends BroadcastReceiver {

    static final String ACTION_FINISH_PIXEL = "finish_pixel";

    @Override public void onReceive(Context context, Intent intent) {
        if (context == null || intent == null || !(context instanceof Activity)) {
            return;
        }
        String action = intent.getAction();
        if (TextUtils.isEmpty(action) || !action.equals(ACTION_FINISH_PIXEL)) {
            return;
        }
        ((Activity) context).finish();
    }
}
