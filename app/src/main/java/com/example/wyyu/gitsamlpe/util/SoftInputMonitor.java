package com.example.wyyu.gitsamlpe.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

/**
 * Created by wyyu on 2018/8/31.
 * 软键盘弹出监听类，实现 Listener，接收 notify 即可
 **/

public class SoftInputMonitor {

    private static final String sHUAWEIManufacturerName = "HUAWEI";

    private View mRootView;
    private View mAttachView;
    private WindowManager mWindowManager;

    private RootViewLayoutListener mRootViewLayoutListener;
    private Listener mListener;

    private boolean mStarted;
    private int mMaxHeightInScreen = -1;
    private int mHeightInScreen = -1;
    private boolean mIsVisible;
    private ViewTreeObserver.OnGlobalLayoutListener mAttachViewLayoutListener =
        new ViewTreeObserver.OnGlobalLayoutListener() {
            final int[] location = new int[2];

            @Override public void onGlobalLayout() {
                if (mAttachView != null) {
                    mAttachView.post(new Runnable() {
                        @Override public void run() {
                            if (mAttachView != null) {
                                mAttachView.getLocationOnScreen(location);
                                int heightInView = mAttachView.getHeight();
                                int heightInScreen = location[1] + heightInView;
                                mMaxHeightInScreen =
                                    mMaxHeightInScreen < heightInScreen ? heightInScreen
                                        : mMaxHeightInScreen;

                                if (mHeightInScreen != heightInScreen) {
                                    int softInputHeight =
                                        Math.max(mMaxHeightInScreen - heightInScreen, 0);
                                    notifyListener(softInputHeight, location[1]);
                                    mHeightInScreen = heightInScreen;
                                }
                            }
                        }
                    });
                }
            }
        };

    public void setListener(Listener listener) {
        mListener = listener;
    }

    private void notifyListener(int softInputHeight, int statusBarHeight) {
        boolean visible = softInputHeight > UIUtils.dpToPx(60);
        if (visible == mIsVisible) return;

        mIsVisible = visible;
        if (mListener != null) {
            mListener.onSoftInputVisibilityChanged(visible, softInputHeight, statusBarHeight);
        }
    }

    public boolean softInputIsShow() {
        return mIsVisible;
    }

    public boolean hasStarted() {
        return mStarted;
    }

    public void startMonitoring(Activity activity) {
        if (mStarted) return;

        mStarted = true;
        Activity rootActivity = activity;
        Activity parent = activity.getParent();
        if (null != parent) {
            rootActivity = parent;
        }
        mRootView = rootActivity.findViewById(android.R.id.content);
        if (mRootView.getWindowToken() != null) {
            attachMonitor(rootActivity, mRootView.getWindowToken());
        } else {
            mRootViewLayoutListener = new RootViewLayoutListener(activity);
            mRootView.getViewTreeObserver().addOnGlobalLayoutListener(mRootViewLayoutListener);
        }
        mMaxHeightInScreen = mRootView.getHeight();
    }

    private void attachMonitor(Activity activity, IBinder token) {
        detachMonitor();
        mWindowManager =
            (WindowManager) activity.getApplication().getSystemService(Context.WINDOW_SERVICE);
        mAttachView = new View(activity);
        String manufacturerName = Build.MANUFACTURER;
        WindowManager.LayoutParams params;
        if (!TextUtils.isEmpty(manufacturerName) && manufacturerName.equals(
            sHUAWEIManufacturerName)) {
            params = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL, 131096, PixelFormat.TRANSLUCENT);
        } else {
            params = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG, 131096,
                PixelFormat.TRANSLUCENT);
        }
        params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
        params.token = token;
        mWindowManager.addView(mAttachView, params);
        mAttachView.getViewTreeObserver().addOnGlobalLayoutListener(mAttachViewLayoutListener);
    }

    public void stopMonitoring() {
        if (!mStarted) return;

        mStarted = false;
        if (mRootViewLayoutListener != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mRootView.getViewTreeObserver()
                    .removeOnGlobalLayoutListener(mRootViewLayoutListener);
            } else {
                mRootView.getViewTreeObserver()
                    .removeGlobalOnLayoutListener(mRootViewLayoutListener);
            }
        }
        mRootView = null;
        detachMonitor();
    }

    private void detachMonitor() {
        if (mAttachView == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mAttachView.getViewTreeObserver().removeOnGlobalLayoutListener(mRootViewLayoutListener);
        } else {
            mAttachView.getViewTreeObserver().removeGlobalOnLayoutListener(mRootViewLayoutListener);
        }
        mWindowManager.removeViewImmediate(mAttachView);
        mAttachView = null;
    }

    public interface Listener {

        /**
         * 将软键盘的弹出状态回调出去
         *
         * @param visible 是否正在展示
         * @param softInputHeight 软键盘高度
         * @param statusBarHeight 状态栏高度
         */
        void onSoftInputVisibilityChanged(boolean visible, int softInputHeight,
            int statusBarHeight);
    }

    class RootViewLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        private final Activity activity;

        public RootViewLayoutListener(Activity activity) {
            this.activity = activity;
        }

        @Override public void onGlobalLayout() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mRootView.getViewTreeObserver()
                    .removeOnGlobalLayoutListener(mRootViewLayoutListener);
            } else {
                mRootView.getViewTreeObserver()
                    .removeGlobalOnLayoutListener(mRootViewLayoutListener);
            }
            mRootViewLayoutListener = null;
            attachMonitor(activity, mRootView.getWindowToken());
        }
    }
}
