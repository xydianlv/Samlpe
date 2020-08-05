package com.example.wyyu.gitsamlpe.test.text.anim;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.wyyu.gitsamlpe.framework.WeakHandler;

/**
 * Created by wyyu on 2020-01-02.
 **/

public class MarqueeTextView extends View implements LifecycleObserver {

    public MarqueeTextView(Context context) {
        super(context);
        initView();
    }

    public MarqueeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MarqueeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private static final int MSG_DEFAULT = 0;

    private WeakHandler handler;
    private String textValue;
    private Paint paint;

    private float x;
    private float y;

    private void initView() {
        initValue();
        initHandler();
    }

    private void initValue() {
        textValue = "";
        x = 40.0f;
        y = 40.0f;

        paint = new Paint();
        paint.setTextSize(36.0f);
        paint.setColor(0xffB27100);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.LEFT);
    }

    private void initHandler() {
        handler = new WeakHandler(msg -> {
            if (msg == null) {
                return;
            }
            x = x - 2.0f;
            invalidate();

            if (handler != null) {
                handler.sendEmptyMessageDelayed(MSG_DEFAULT, 6);
            }
        });
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(textValue, x, y, paint);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY) public void destroyAnim() {
        if (handler != null) {
            handler.removeMessages(MSG_DEFAULT);
        }
        handler = null;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
        if (handler == null) {
            initHandler();
        }
        handler.sendEmptyMessageDelayed(MSG_DEFAULT, 6);
    }
}
