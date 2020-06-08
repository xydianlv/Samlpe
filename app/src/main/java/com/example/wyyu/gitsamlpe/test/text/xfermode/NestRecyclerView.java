package com.example.wyyu.gitsamlpe.test.text.xfermode;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wyyu on 2020-06-08.
 **/

public class NestRecyclerView extends RecyclerView {

    public NestRecyclerView(@NonNull Context context) {
        super(context);
    }

    public NestRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NestRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private boolean onBottom;
    private boolean onTop;

    private float startY;

    @Override public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            onBottom = !canScrollVertically(1);
            onTop = !canScrollVertically(-1);
        }
    }

    @Override public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getRawY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:

                float divideY = event.getRawY() - startY;
                if (divideY == 0) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else if (divideY < 0) {
                    // 手指上滑
                    getParent().requestDisallowInterceptTouchEvent(!onBottom);
                } else {
                    // 手指下滑
                    getParent().requestDisallowInterceptTouchEvent(!onTop);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
