package com.example.wyyu.gitsamlpe.framework;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wyyu on 2018/4/10.
 **/

public class StableViewPager extends ViewPager {

    public StableViewPager(@NonNull Context context) {
        super(context);
    }

    public StableViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    float mLastX;
    float mLastY;

    @Override public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getAdapter() != null && getAdapter().getCount() <= 1) {
            //只有一张图时不要拦截滑动事件
            getParent().requestDisallowInterceptTouchEvent(false);
            return super.dispatchTouchEvent(ev);
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = ev.getX();
                mLastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                float xMoveValue = ev.getX() - mLastX;

                float yDiff = Math.abs(ev.getY() - mLastY);
                float xDiff = Math.abs(xMoveValue);

                if (yDiff * 0.5f > xDiff) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    if (xMoveValue < 0 || getCurrentItem() > 0) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                mLastX = ev.getX();
                mLastY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View child = getChildAt(getCurrentItem());
        if (child != null) {
            child.measure(widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int height = child.getMeasuredHeight();
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
