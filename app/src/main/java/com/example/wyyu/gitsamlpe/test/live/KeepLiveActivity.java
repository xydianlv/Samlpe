package com.example.wyyu.gitsamlpe.test.live;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by wyyu on 2019/1/8.
 **/

public class KeepLiveActivity extends Activity {

    public static void open(Context context) {
        Intent intent = new Intent(context, KeepLiveActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private FinishPixelReceiver finishPixelReceiver;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActivity();
        initReceiver();
    }

    @Override protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(finishPixelReceiver);
    }

    private void initActivity() {
        Window window = getWindow();
        window.setGravity(51);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.width = 1;
        layoutParams.height = 1;
        window.setAttributes(layoutParams);
    }

    private void initReceiver() {
        finishPixelReceiver = new FinishPixelReceiver();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FinishPixelReceiver.ACTION_FINISH_PIXEL);

        registerReceiver(finishPixelReceiver, intentFilter);
    }
}
