package com.example.wyyu.gitsamlpe.framework.window;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.example.wyyu.gitsamlpe.framework.ULog;

/**
 * Created by wyyu on 2019/3/11.
 **/

public class PressListenerView extends FrameLayout {

    public PressListenerView(@NonNull Context context) {
        super(context);
    }

    public PressListenerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PressListenerView(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override public boolean dispatchTouchEvent(MotionEvent ev) {

        try {
            if (pressListener == null) {
                return super.dispatchTouchEvent(ev);
            }

            boolean result = super.dispatchTouchEvent(ev);

            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    return pressListener.onPressDown();
                case MotionEvent.ACTION_UP:
                    return pressListener.onPressUp();
            }
            return result;
        } catch (Exception ex) {
            ULog.show(ex.getMessage());
            return false;
        }
    }

    public interface OnPressListener {

        boolean onPressDown();

        boolean onPressUp();
    }

    private OnPressListener pressListener;

    public void setOnPressListener(OnPressListener pressListener) {
        this.pressListener = pressListener;
    }
}
