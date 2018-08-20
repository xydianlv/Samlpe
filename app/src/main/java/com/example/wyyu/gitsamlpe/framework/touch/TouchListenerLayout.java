package com.example.wyyu.gitsamlpe.framework.touch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by wyyu on 2018/5/31.
 **/

public class TouchListenerLayout extends FrameLayout {

    public TouchListenerLayout(Context context) {
        super(context);
    }

    public TouchListenerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchListenerLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float pressFunX;
    private float pressFunY;

    @Override public boolean dispatchTouchEvent(MotionEvent ev) {

        if (pressListenerAdapter == null) {
            return super.dispatchTouchEvent(ev);
        }

        boolean result = super.dispatchTouchEvent(ev);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pressListenerAdapter.onPressDown();
                pressFunX = ev.getRawX();
                pressFunY = ev.getRawY();
                return true;
            case MotionEvent.ACTION_MOVE:
                pressListenerAdapter.onPressMove();
                checkScrollDirection(ev.getRawX(), ev.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                pressListenerAdapter.onPressUp();
                break;
        }
        return result;
    }

    private void checkScrollDirection(float rawX, float rawY) {
        float dRawX = rawX - pressFunX;
        float dRawY = rawY - pressFunY;

        if (Math.abs(dRawX) > Math.abs(dRawY)) {
            if (dRawX > 0) {
                pressListenerAdapter.onScrollLeft();
            } else {
                pressListenerAdapter.onScrollRight();
            }
        } else {
            if (dRawY > 0) {
                pressListenerAdapter.onScrollTop();
            } else {
                pressListenerAdapter.onScrollBottom();
            }
        }
    }

    public interface OnPressListener {

        // 右滑监听，手指从右向左滑
        void onScrollRight();

        // 左滑监听，手指从左向右滑
        void onScrollLeft();

        // 下滑监听，手指从下往上滑
        void onScrollBottom();

        // 上滑监听，手指从上往下滑
        void onScrollTop();

        void onPressDown();

        void onPressMove();

        void onPressUp();
    }

    private OnPressListener pressListenerAdapter;

    public void setOnPressListener(OnPressListenerAdapter pressListenerAdapter) {
        this.pressListenerAdapter = pressListenerAdapter;
    }
}
