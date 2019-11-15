package com.wyyu.click_expand;

/**
 * Created by wyyu on 2019-11-15.
 **/

public class ClickUtils {

    // 点击间隔时间
    private static final long DIVIDE_TIME = 600L;
    // 点击的控件ID
    private static long viewId;
    // 最近一次点击屏幕的时间戳
    private static long lastTime;

    /**
     * 是否重复点击
     *
     * @param viewId ViewId
     * @return 是否在短期内已经点击过该控件
     */
    public static boolean hasClick(long viewId) {
        if (ClickUtils.viewId == viewId) {
            long divide = System.currentTimeMillis() - ClickUtils.lastTime;
            ClickUtils.lastTime = System.currentTimeMillis();
            return divide < +ClickUtils.DIVIDE_TIME;
        } else {
            ClickUtils.viewId = viewId;
            ClickUtils.lastTime = System.currentTimeMillis();
            return false;
        }
    }
}
