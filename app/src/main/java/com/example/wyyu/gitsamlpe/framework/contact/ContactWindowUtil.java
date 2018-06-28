package com.example.wyyu.gitsamlpe.framework.contact;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by wyyu on 2018/6/28.
 **/

public class ContactWindowUtil {

    private ContactView contactView;
    private View dialogView;
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private WindowManager.LayoutParams dialogParams;
    private Activity activity;
    private ContactWindowListener mListener;
    private ValueAnimator valueAnimator;
    private int direction;
    private final int LEFT = 0;
    private final int RIGHT = 1;

    public interface ContactWindowListener {
        void onDataCallBack(String str);
    }

    public void setDialogListener(ContactWindowListener listener) {
        mListener = listener;
    }

    //私有化构造函数
    public ContactWindowUtil(Activity activity) {
        this.activity = activity;
        windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
              //windowManager = (WindowManager) AppController.getAppContext().getSystemService(Context.WINDOW_SERVICE);
    }

    public void showContactView() {
        hideContactView();
        contactView = new ContactView(activity);
        if (layoutParams == null) {
            layoutParams = new WindowManager.LayoutParams();
            layoutParams.width = contactView.width;
            layoutParams.height = contactView.height;
            layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
            if (Build.VERSION.SDK_INT > 18 && Build.VERSION.SDK_INT < 23) {
                layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
            } else {
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
            }
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            layoutParams.format = PixelFormat.RGBA_8888;
        }
        windowManager.addView(contactView, layoutParams);
    }

    public void hideAllView() {
        hideContactView();
        hideDialogView();
    }

    public void hideContactView() {
        if (contactView != null) {
            windowManager.removeView(contactView);
            contactView = null;
        }
    }

    public void hideDialogView() {
        if (dialogView != null) {
            windowManager.removeView(dialogView);
            dialogView = null;
        }
    }
}
