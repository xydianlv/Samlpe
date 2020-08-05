package com.example.wyyu.gitsamlpe.framework;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wyyu on 2019/1/9.
 **/

public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        performClick();
        return false;
    }

    @Override public boolean performClick() {
        return super.performClick();
    }

    @Override public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }
}
