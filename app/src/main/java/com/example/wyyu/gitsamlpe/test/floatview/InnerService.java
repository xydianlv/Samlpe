package com.example.wyyu.gitsamlpe.test.floatview;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

/**
 * Created by wyyu on 2019/1/4.
 **/

public class InnerService extends Service {

    @Nullable @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        KeepLiveManager.getManager().setForeground(this, this);
        return super.onStartCommand(intent, flags, startId);
    }
}
