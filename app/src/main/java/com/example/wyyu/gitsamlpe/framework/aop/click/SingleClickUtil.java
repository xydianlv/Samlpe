package com.example.wyyu.gitsamlpe.framework.aop.click;

/**
 * Created by wyyu on 2019-11-18.
 **/

public class SingleClickUtil {

    // 默认单击时间间隔
    public static final long TIME_DIVIDE = 600L;

    // 上一次单击屏幕的时间戳
    private static long lastClickTime = 0;

    // 上一次单击屏幕的 ViewId
    // 记录 ViewId 存在一个问题，单次点击触发多个响应事件时，这个记录的 ViewId 就会出现判断问题
    private static long lastViewId = 0;

    public static boolean hasClick(long viewId) {
        return SingleClickUtil.hasClick(viewId, TIME_DIVIDE);
    }

    public static boolean hasClick(long viewId, long timeDivide) {
        if (viewId != lastViewId) {
            lastClickTime = System.currentTimeMillis();
            lastViewId = viewId;
            return false;
        }
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > timeDivide) {
            lastClickTime = currentTime;
            return false;
        }
        return true;
    }
}
