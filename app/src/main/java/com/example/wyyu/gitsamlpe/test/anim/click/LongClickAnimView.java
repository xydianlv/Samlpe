package com.example.wyyu.gitsamlpe.test.anim.click;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.wyyu.gitsamlpe.framework.WeakHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2020-06-17.
 **/

public class LongClickAnimView extends View {

    public LongClickAnimView(Context context) {
        super(context);
        initAnimView();
    }

    public LongClickAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAnimView();
    }

    public LongClickAnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAnimView();
    }

    private static final int DIVIDE_RATE = 200;
    static final int DIVIDE_ANIM = 6;

    private static final int MSG_RATE = 0;
    private static final int MSG_ANIM = 1;

    private List<Group> groupList;
    private List<Group> funList;

    private WeakHandler handlerAnim;
    private WeakHandler handlerRate;

    private boolean onAnimEnd;
    private int[] location;

    private void initAnimView() {
        groupList = new ArrayList<>();
        location = new int[2];
        onAnimEnd = false;

        initHandler();
    }

    private void initHandler() {
        handlerRate = new WeakHandler(msg -> {
            if (msg == null || msg.what != MSG_RATE || onAnimEnd) {
                return;
            }
            if (groupList == null) {
                groupList = new ArrayList<>();
            }
            groupList.add(new Group(getContext(), location[0], location[1]));

            if (handlerRate != null) {
                handlerRate.removeMessages(MSG_RATE);
                handlerRate.sendEmptyMessageDelayed(MSG_RATE, DIVIDE_RATE);
            }
            if (handlerAnim != null) {
                handlerAnim.removeMessages(MSG_ANIM);
                handlerAnim.sendEmptyMessage(MSG_ANIM);
            }
        });
        handlerAnim = new WeakHandler(msg -> {
            if (msg == null || msg.what != MSG_ANIM) {
                return;
            }
            if (groupList == null || groupList.isEmpty()) {
                clearAnimValue();
                return;
            }
            if (funList == null) {
                funList = new ArrayList<>();
            }
            funList.clear();
            for (Group group : groupList) {
                if (group == null) {
                    continue;
                }
                if (group.refreshProgress()) {
                    funList.add(group);
                }
            }
            if (funList != null && !funList.isEmpty()) {
                groupList.removeAll(funList);
            }
            invalidate();

            if (handlerAnim != null) {
                handlerAnim.removeMessages(MSG_ANIM);
                handlerAnim.sendEmptyMessageDelayed(MSG_ANIM, DIVIDE_ANIM);
            }
        });
    }

    private void clearAnimValue() {
        if (handlerAnim != null) {
            handlerAnim.removeMessages(MSG_ANIM);
        }
        if (handlerRate != null) {
            handlerRate.removeMessages(MSG_RATE);
        }
        if (groupList != null) {
            groupList.clear();
        }
        if (funList != null) {
            funList.clear();
        }
        onAnimEnd = false;
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
            if (value.leftTime == AnimView.DURATION_ANIM) {
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
     * 展示长按动画
     *
     * @param anchorView 锚点View
     */
    public void showAnim(@NonNull View anchorView) {
        anchorView.getLocationOnScreen(location);
        if (handlerRate == null || handlerAnim == null) {
            initHandler();
        }
        onAnimEnd = false;
        handlerRate.sendEmptyMessage(MSG_RATE);
    }

    /**
     * 停止动画
     */
    public void destroyAnim() {
        onAnimEnd = true;
    }
}
