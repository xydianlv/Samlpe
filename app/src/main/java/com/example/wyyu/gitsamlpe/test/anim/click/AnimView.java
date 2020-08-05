package com.example.wyyu.gitsamlpe.test.anim.click;

import android.content.Context;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.wyyu.gitsamlpe.framework.WeakHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2020-06-18.
 **/

public class AnimView extends View {

    public AnimView(Context context) {
        super(context);
        initView();
    }

    public AnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public interface OnRateListener {

        void onRate();
    }

    private static final int MSG_ANIM = 1;
    private static final int MSG_RATE = 2;

    static final int DURATION_ANIM = 250;
    static final int DIVIDE_RATE = 100;
    static final int DIVIDE_ANIM = 6;

    private List<Group> groupList;
    private List<Group> funList;

    private OnRateListener rateListener;
    private WeakHandler handler;

    private int x;
    private int y;

    private void initView() {
        groupList = new ArrayList<>();
        funList = new ArrayList<>();
        x = 0;
        y = 0;

        initHandler();
    }

    private void initHandler() {
        handler = new WeakHandler(msg -> {
            if (msg == null) {
                return;
            }
            if (msg.what == MSG_ANIM) {
                dispatchInvalidate();
            }
            if (msg.what == MSG_RATE) {
                dispatchRate();
            }
        });
    }

    private void dispatchInvalidate() {
        if (groupList == null || groupList.isEmpty()) {
            clearValue();
            return;
        }
        if (funList == null) {
            funList = new ArrayList<>();
        }
        funList.clear();
        for (Group group : groupList) {
            if (group.refreshProgress()) {
                funList.add(group);
            }
        }
        if (funList != null && !funList.isEmpty()) {
            groupList.removeAll(funList);
        }
        invalidate();

        if (handler != null) {
            handler.removeMessages(MSG_ANIM);
            handler.sendEmptyMessageDelayed(MSG_ANIM, DIVIDE_ANIM);
        }
    }

    private void dispatchRate() {
        if (rateListener != null) {
            rateListener.onRate();
        }
        if (groupList == null) {
            groupList = new ArrayList<>();
        }
        groupList.add(new Group(getContext(), x, y));
        if (handler != null) {
            handler.removeMessages(MSG_RATE);
            handler.sendEmptyMessageDelayed(MSG_RATE, DIVIDE_RATE);
        }
    }

    private void clearValue() {
        if (groupList != null) {
            groupList.clear();
            groupList = null;
        }
        if (funList != null) {
            funList.clear();
            funList = null;
        }
        if (handler != null) {
            handler.removeMessages(MSG_ANIM);
            handler = null;
        }
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (groupList == null || groupList.isEmpty()) {
            return;
        }
        for (Group value : groupList) {
            if (value == null || value.valueArray == null || value.valueArray.length <= 0) {
                continue;
            }
            if (value.leftTime == DURATION_ANIM) {
                continue;
            }
            for (int index = value.valueArray.length - 1; index >= 0; index--) {
                Element element = value.valueArray[index];
                if (element == null) {
                    continue;
                }
                canvas.drawBitmap(element.img, element.matrix, element.paint);
            }
        }
    }

    /**
     * 设置元素动画频率监听
     *
     * @param rateListener 频率监听回调
     */
    void setRateListener(OnRateListener rateListener) {
        this.rateListener = rateListener;
    }

    /**
     * 添加一组新动画
     *
     * @param x 坐标X
     * @param y 坐标Y
     */
    void showAnim(int x, int y) {
        this.x = x;
        this.y = y;

        if (handler == null) {
            initHandler();
        }
        handler.removeMessages(MSG_RATE);
        handler.sendEmptyMessage(MSG_RATE);

        handler.removeMessages(MSG_ANIM);
        handler.sendEmptyMessage(MSG_ANIM);
    }

    /**
     * 停止动画
     */
    void endAnim() {
        if (handler != null) {
            handler.removeMessages(MSG_RATE);
        }
    }
}
