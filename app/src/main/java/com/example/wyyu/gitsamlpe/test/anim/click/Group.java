package com.example.wyyu.gitsamlpe.test.anim.click;

import android.content.Context;
import android.support.annotation.NonNull;
import com.example.wyyu.gitsamlpe.R;
import java.util.Random;

/**
 * Created by wyyu on 2020-06-17.
 **/

class Group {

    // 上扬的图标列表
    private static final int[] ICON_ARRAY = new int[] {
        R.mipmap.long_anim_element_a, R.mipmap.long_anim_element_b, R.mipmap.long_anim_element_c,
        R.mipmap.long_anim_element_d, R.mipmap.long_anim_element_e, R.mipmap.long_anim_element_f,
        R.mipmap.long_anim_element_g,
    };
    // 乱序数组
    private static final int[] ARRAY_MESS = new int[] { 0, 1, 2, 3, 4, 5, 6, 0, 3, 6 };

    // 动画元素数据
    Element[] valueArray;
    // 剩余时间
    long leftTime;

    Group(@NonNull Context context, float left, float top) {
        leftTime = AnimView.DURATION_ANIM;

        Random random = new Random();
        // 六个到十个小图标
        int numIcon = random.nextInt(4) + 6;

        for (int index = ARRAY_MESS.length - 1; index > 0; index--) {
            int position = random.nextInt(index);
            ARRAY_MESS[index] = ARRAY_MESS[index] + ARRAY_MESS[position];
            ARRAY_MESS[position] = ARRAY_MESS[index] - ARRAY_MESS[position];
            ARRAY_MESS[index] = ARRAY_MESS[index] - ARRAY_MESS[position];
        }

        valueArray = new Element[numIcon];

        for (int index = 0; index < numIcon; index++) {
            valueArray[index] =
                new Element(context, ICON_ARRAY[ARRAY_MESS[index]], left, top, index);
        }
    }

    // 刷新剩余时长，该动画组是否可被移除
    boolean refreshProgress() {
        if (valueArray == null || valueArray.length <= 0) {
            return true;
        }
        leftTime = leftTime - AnimView.DIVIDE_ANIM;
        if (leftTime <= 0) {
            valueArray = null;
            return true;
        }
        float progress = 1 - (leftTime * 1.0f / AnimView.DURATION_ANIM);
        for (Element value : valueArray) {
            value.onProgress(progress);
        }
        return false;
    }
}
