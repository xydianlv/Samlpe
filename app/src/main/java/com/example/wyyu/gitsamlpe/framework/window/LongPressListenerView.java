package com.example.wyyu.gitsamlpe.framework.window;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.WeakHandler;

/**
 * Created by wyyu on 2020/10/13.
 **/

public class LongPressListenerView extends FrameLayout {

    public LongPressListenerView(@NonNull Context context) {
        this(context, null);
    }

    public LongPressListenerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LongPressListenerView(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHandler();
    }

    private static final int LONG_PRESS_TIME_DIVIDE = 500;
    private static final int MSG_LONG_PRESS = 0;

    public static boolean onScroll = false;

    private WeakHandler handler;
    private boolean onLongPress;

    private void initHandler() {
        handler = new WeakHandler(msg -> {
            if (msg == null || msg.what != MSG_LONG_PRESS || pressListener == null) {
                return;
            }
            if (LongPressListenerView.onScroll) {
                return;
            }
            if (getParent() != null) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            onLongPress = pressListener.onLongPress();
        });
    }

    @Override public boolean dispatchTouchEvent(MotionEvent ev) {

        try {
            if (pressListener == null) {
                return super.dispatchTouchEvent(ev);
            }

            boolean result = super.dispatchTouchEvent(ev);

            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (handler != null) {
                        handler.sendEmptyMessageDelayed(MSG_LONG_PRESS, LONG_PRESS_TIME_DIVIDE);
                    }
                    return pressListener.onPressDown();
                case MotionEvent.ACTION_UP:
                    if (getParent() != null) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    if (handler != null) {
                        handler.removeMessages(MSG_LONG_PRESS);
                    }
                    if (onLongPress) {
                        onLongPress = false;
                        return pressListener.onLongPressUp();
                    }
                    return pressListener.onPressUp();
            }
            return result;
        } catch (Exception ex) {
            ULog.show(ex.getMessage());
            return false;
        }
    }

    public interface OnPressListener {

        boolean onLongPressUp();

        boolean onLongPress();

        boolean onPressDown();

        boolean onPressUp();
    }

    private LongPressListenerView.OnPressListener pressListener;

    public void setOnPressListener(LongPressListenerView.OnPressListener pressListener) {
        this.pressListener = pressListener;
    }
}
