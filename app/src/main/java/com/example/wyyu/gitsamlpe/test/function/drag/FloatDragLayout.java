package com.example.wyyu.gitsamlpe.test.function.drag;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2021/4/6.
 **/

public class FloatDragLayout extends FrameLayout {

    public FloatDragLayout(@NonNull Context context) {
        super(context);
        initDragView();
    }

    public FloatDragLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDragView();
    }

    public FloatDragLayout(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDragView();
    }

    private static final int MID_HORIZONTAL = UIUtils.getScreenWidth() / 2;

    private ViewDragHelper dragHelper;

    private void initDragView() {
        dragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                return left;
            }

            @Override public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                return top;
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                int curTop = releasedChild.getTop();
                int viewX = releasedChild.getWidth() / 2 + releasedChild.getLeft();
                if (viewX < MID_HORIZONTAL) {
                    dragHelper.settleCapturedViewAt(0, curTop);
                } else {
                    dragHelper.settleCapturedViewAt(MID_HORIZONTAL * 2 - releasedChild.getWidth(),
                        curTop);
                }
                invalidate();
            }
        });
    }

    @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        performClick();
        dragHelper.processTouchEvent(event);
        return isTouchChildView(event);
    }

    @Override public boolean performClick() {
        return super.performClick();
    }

    @Override public void computeScroll() {
        super.computeScroll();
        if (dragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    private boolean isTouchChildView(MotionEvent ev) {
        Rect rect = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            rect.set((int) view.getX(), (int) view.getY(), (int) view.getX() + view.getWidth(),
                (int) view.getY() + view.getHeight());
            if (rect.contains((int) ev.getX(), (int) ev.getY())) {
                return true;
            }
        }
        return false;
    }
}
