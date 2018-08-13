package com.example.wyyu.gitsamlpe.framework.volume;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by wyyu on 2018/8/11.
 **/

public class VolumeReceiver extends BroadcastReceiver {

    private static final String VOLUME_CHANGE_ACTION = "android.media.VOLUME_CHANGED_ACTION";

    @Override public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(VOLUME_CHANGE_ACTION)) {
            VolumeObservable.getObservable().onVolumeValueChange();
        }
    }
}
