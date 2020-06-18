package com.example.wyyu.gitsamlpe.test.anim.click;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
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

    private static final int DIVIDE_ANIM = 6;
    private static final int MSG_ANIM = 1;

    static final int DURATION_ANIM = 160;

    private List<Group> groupList;
    private List<Group> funList;

    private WeakHandler handler;

    private void initView() {
        groupList = new ArrayList<>();
        funList = new ArrayList<>();

        initHandler();
    }

    private void initHandler() {
        handler = new WeakHandler(msg -> {
            if (msg == null || msg.what != MSG_ANIM) {
                return;
            }
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
        });
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
     * 添加一组新动画
     *
     * @param x 坐标X
     * @param y 坐标Y
     */
    void showAnim(int x, int y) {
        if (groupList == null) {
            groupList = new ArrayList<>();
        }
        groupList.add(new Group(getContext(), x, y));
        if (handler == null) {
            initHandler();
        }
        handler.removeMessages(MSG_ANIM);
        handler.sendEmptyMessage(MSG_ANIM);
    }
}
