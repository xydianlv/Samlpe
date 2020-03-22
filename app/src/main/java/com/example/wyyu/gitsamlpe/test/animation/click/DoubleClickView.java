package com.example.wyyu.gitsamlpe.test.animation.click;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.example.wyyu.gitsamlpe.framework.WeakHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2020-03-22.
 **/

public class DoubleClickView extends View {

    public DoubleClickView(Context context) {
        super(context);
        initClickView();
    }

    public DoubleClickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initClickView();
    }

    public DoubleClickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initClickView();
    }

    private static final int MSG_DEFAULT = 0;
    static final int TIME_DIVIDE = 6;

    private List<AnimValue> valueList;
    private List<AnimValue> funList;
    private WeakHandler handler;

    private GestureDetector detector;

    private void initClickView() {
        valueList = new ArrayList<>();
        funList = new ArrayList<>();

        initHandler();
        initDetector();
    }

    private void initHandler() {
        handler = new WeakHandler(msg -> {
            if (msg == null || msg.what != MSG_DEFAULT) {
                return;
            }
            if (valueList == null || valueList.isEmpty()) {
                return;
            }
            if (funList == null) {
                funList = new ArrayList<>();
            }
            funList.clear();
            for (AnimValue value : valueList) {
                if (value.refreshProgress()) {
                    funList.add(value);
                }
            }
            if (funList != null && !funList.isEmpty()) {
                valueList.removeAll(funList);
            }
            invalidate();
            if (valueList != null && !valueList.isEmpty()) {
                handler.sendEmptyMessageDelayed(MSG_DEFAULT, TIME_DIVIDE);
            }
        });
    }

    private void initDetector() {
        detector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            public boolean onDown(MotionEvent e) {
                return true;
            }

            public boolean onDoubleTap(MotionEvent e) {
                if (valueList == null) {
                    valueList = new ArrayList<>();
                }
                if (handler == null) {
                    initHandler();
                }
                valueList.add(new AnimValue(getContext(), e.getX(), e.getY()));
                handler.removeMessages(MSG_DEFAULT);

                handler.sendEmptyMessage(MSG_DEFAULT);
                return true;
            }

            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (valueList == null || valueList.isEmpty()) {
            return;
        }
        for (AnimValue value : valueList) {
            if (value == null || value.valueArray == null || value.valueArray.length <= 0) {
                continue;
            }
            for (ClickValue clickValue : value.valueArray) {
                if (clickValue == null) {
                    continue;
                }
                canvas.drawBitmap(clickValue.img, clickValue.matrix, clickValue.paint);
            }
        }
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        performClick();
        if (detector == null) {
            initDetector();
        }
        return detector.onTouchEvent(event);
    }

    @Override public boolean performClick() {
        return super.performClick();
    }
}
