package com.example.wyyu.gitsamlpe.util.guide;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.WindowManager;
import java.util.concurrent.TimeUnit;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * Created by wyyu on 2018/10/12.
 * 一个无论处于任何位置都可以弹出来的浮层显示策略
 **/

public class TestGuide {

    private WindowManager.LayoutParams layoutParams;

    public TestGuide() {
        layoutParams = new WindowManager.LayoutParams();

        layoutParams.gravity = Gravity.CENTER;

        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        if (Build.VERSION.SDK_INT > 18 && Build.VERSION.SDK_INT < 23) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
    }

    public void show(Activity activity) {

        final WindowManager windowManager =
            (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        final TestView testView = new TestView(activity);
        if (windowManager == null) {
            return;
        }
        windowManager.addView(testView, layoutParams);

        // 三秒后消失
        AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
            @Override public void call() {
                windowManager.removeView(testView);
            }
        }, 3000, TimeUnit.MILLISECONDS);
    }
}
