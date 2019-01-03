package com.example.wyyu.gitsamlpe.test.floatview;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by wyyu on 2019/1/3.
 **/

public class FloatingService extends Service {

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable @Override public IBinder onBind(Intent intent) {
        return null;
    }

    private void showFloatingWindow() {
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        if (windowManager == null) {
            return;
        }

        // 新建悬浮窗控件
        View view = new View(getApplicationContext());
        view.setBackgroundColor(0xffff0000);

        // 设置LayoutParam
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.width = 10;
        layoutParams.height = 10;
        layoutParams.x = 0;
        layoutParams.y = 0;

        // 将悬浮窗控件添加到WindowManager
        windowManager.addView(view, layoutParams);
    }
}
