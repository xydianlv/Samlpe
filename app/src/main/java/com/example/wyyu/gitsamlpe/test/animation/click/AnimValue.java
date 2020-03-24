package com.example.wyyu.gitsamlpe.test.animation.click;

import android.content.Context;
import android.support.annotation.NonNull;
import com.example.wyyu.gitsamlpe.R;
import java.util.Random;

/**
 * Created by wyyu on 2020-03-22.
 **/

class AnimValue {

    // 上扬的图标列表
    private static final int[] ICON_ARRAY = new int[] {
        R.mipmap.anim_icon_a, R.mipmap.anim_icon_b, R.mipmap.anim_icon_c, R.mipmap.anim_icon_d,
        R.mipmap.anim_icon_e, R.mipmap.anim_icon_f, R.mipmap.anim_icon_g,
    };
    // 乱序数组
    private static final int[] ARRAY_MESS = new int[] { 0, 1, 2, 3, 4, 5, 6 };
    // 动画主图标
    private static final int MAIN_IMAGE = R.mipmap.anim_click_img;

    // 动画时长
    static final long DURATION = 300;

    // 动画元素数据
    ClickValue[] valueArray;
    // 剩余时间
    long leftTime;

    AnimValue(@NonNull Context context, float left, float top) {
        leftTime = DURATION;

        Random random = new Random();
        // 三个或四个小图标
        int numIcon = random.nextInt(2) + 3;

        for (int index = ARRAY_MESS.length - 1; index > 0; index--) {
            int position = random.nextInt(index);
            ARRAY_MESS[index] = ARRAY_MESS[index] + ARRAY_MESS[position];
            ARRAY_MESS[position] = ARRAY_MESS[index] - ARRAY_MESS[position];
            ARRAY_MESS[index] = ARRAY_MESS[index] - ARRAY_MESS[position];
        }

        valueArray = new ClickValue[numIcon + 1];
        valueArray[0] = new ClickValue(context, MAIN_IMAGE, left, top, 0);

        for (int index = 1; index <= numIcon; index++) {
            valueArray[index] =
                new ClickValue(context, ICON_ARRAY[ARRAY_MESS[index]], left, top, index);
        }
    }

    // 刷新剩余时长，该动画组是否可被移除
    boolean refreshProgress() {
        if (valueArray == null || valueArray.length <= 0) {
            return true;
        }
        leftTime = leftTime - DoubleClickView.TIME_DIVIDE;
        if (leftTime <= 0) {
            valueArray = null;
            return true;
        }
        float progress = 1 - (leftTime * 1.0f / DURATION);
        for (ClickValue value : valueArray) {
            value.onProgress(progress);
        }
        return false;
    }
}
