package com.example.wyyu.gitsamlpe.framework.volume;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.WindowManager;

/**
 * Created by wyyu on 2018/8/11.
 **/

public class VolumeSeekView implements IVolumeSeekView {

    private WindowManager.LayoutParams layoutParams;
    private WindowManager windowManager;

    private SeekView seekView;

    VolumeSeekView() {
        layoutParams = new WindowManager.LayoutParams();

        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

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

    @Override public void refreshProgress(int progress) {
        if (seekView != null) {
            seekView.setProgress(progress);
        }
    }

    @Override public void show(Activity activity, int max, int progress) {
        windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager == null) {
            return;
        }
        seekView = new SeekView(activity);
        seekView.setProgress(progress);
        seekView.setMax(max);

        windowManager.addView(seekView, layoutParams);
    }

    @Override public void hide() {
        if (seekView == null || windowManager == null) {
            return;
        }
        windowManager.removeView(seekView);
        windowManager = null;
        seekView = null;
    }
}
