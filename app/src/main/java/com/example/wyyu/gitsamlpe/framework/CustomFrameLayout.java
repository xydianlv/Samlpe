package com.example.wyyu.gitsamlpe.framework;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class CustomFrameLayout extends FrameLayout {

    private View presentView;
    private int[] list;

    public CustomFrameLayout(Context context) {
        super(context);
    }

    public CustomFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    // 获取当前前台显示的view
    public View getCurForgroundView() {
        if (presentView == null)
            return getChildAt(0);
        else
            return presentView;
    }


    // 设置子面板id数组
    public void setList(int[] list) {
        this.list = list;
        showAppointedLayout(0);
    }

    // 显示某个面板
    public void showAppointedLayout(int id) {
        if (list == null) {
            for (int i = 0; i < getChildCount(); ++i) {
                View view = getChildAt(i);

                if (id == view.getId()) {
                    presentView = view;
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
            }

            return;
        }

        for (int aList : list) {
            View item = findViewById(aList);
            if (item == null) {
                continue;
            }
            if (aList == id) {
                presentView = item;
                item.setVisibility(View.VISIBLE);
            } else {
                item.setVisibility(View.GONE);
            }
        }
    }
}
