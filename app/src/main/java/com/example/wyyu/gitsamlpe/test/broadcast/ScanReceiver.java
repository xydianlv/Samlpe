package com.example.wyyu.gitsamlpe.test.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;

/**
 * Created by wyyu on 2018/10/17.
 **/

public class ScanReceiver extends BroadcastReceiver {

    @Override public void onReceive(Context context, Intent intent) {
        UToast.showShort(AppController.getAppContext(), "OnReceive");
    }
}
