package com.example.wyyu.gitsamlpe.framework.contact;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.WindowManager;
import com.example.wyyu.gitsamlpe.test.dialog.ProgressContainer;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2018/6/28.
 **/

public class GlobalFloatView implements IGlobalFloatView {

    private WindowManager.LayoutParams layoutParams;
    private ProgressContainer progressContainer;
    private WindowManager windowManager;

    public GlobalFloatView() {
        layoutParams = new WindowManager.LayoutParams();

        layoutParams.gravity = Gravity.BOTTOM | Gravity.END;
        layoutParams.height = UIUtils.dpToPx(800.0f);
        layoutParams.width = UIUtils.dpToPx(1420.0f);

        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        if (Build.VERSION.SDK_INT > 18 && Build.VERSION.SDK_INT < 23) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
    }

    @Override public void refreshDownloadValue(float percent) {
        if (progressContainer != null) {
            progressContainer.refreshProgressPercent((int) (percent * 100));
        }
    }

    @Override public void show(Activity activity) {
        windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        progressContainer = new ProgressContainer(activity);
        windowManager.addView(progressContainer, layoutParams);
    }

    @Override public void hide() {
        if (progressContainer == null && windowManager == null) {
            return;
        }
        windowManager.removeView(progressContainer);
        progressContainer = null;
        windowManager = null;
    }
}
