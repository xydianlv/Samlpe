package com.example.wyyu.gitsamlpe.test.pager.vertical;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by wyyu on 2021/3/18.
 **/

public class VerticalViewPager extends ViewPager {

    public VerticalViewPager(Context context) {
        super(context);
        initPager(context);
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPager(context);
    }

    private float touchSlop;
    private float downX;
    private float downY;

    private void initPager(Context context) {
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = super.onInterceptTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = x;
                downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - downX);
                float dy = Math.abs(y - downY);

                if (dy > touchSlop && dy > dx) {
                    intercept = true;
                }
                break;
            default:
                break;
        }
        return intercept;
    }

    @Override public boolean onTouchEvent(MotionEvent ev) {
        super.performClick();
        float width = getWidth();
        float height = getHeight();

        MotionEvent event = MotionEvent.obtain(ev);
        event.setLocation((event.getY() / height) * width, (event.getX() / width) * height);

        return super.onTouchEvent(event);
    }

    @Override public boolean performClick() {
        return super.performClick();
    }
}