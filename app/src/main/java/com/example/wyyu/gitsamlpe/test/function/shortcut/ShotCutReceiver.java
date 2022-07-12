package com.example.wyyu.gitsamlpe.test.function.shortcut;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.wyyu.gitsamlpe.framework.toast.UToast;

public class ShotCutReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Constants.SHOT_CUT_ACTION.equals(action)) {
            String id = intent.getStringExtra(Constants.SHOT_CUT_EXTRA_ID_KEY);
            String label = intent.getStringExtra(Constants.SHOT_CUT_EXTRA_LABEL_KEY);
            if (id != null && label != null) {
                UToast.showShort(context, "创建成功");
            }
        }
    }
}
