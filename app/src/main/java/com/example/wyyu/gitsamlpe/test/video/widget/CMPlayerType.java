package com.example.wyyu.gitsamlpe.test.video.widget;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wyyu on 2020-07-28.
 **/

@IntDef({
    CMPlayerType.HEIGHT_THAN_WIDTH, CMPlayerType.WIDTH_THAN_HEIGHT,
    CMPlayerType.WIDTH_THAN_MIN_HEIGHT
}) @Retention(RetentionPolicy.SOURCE) public @interface CMPlayerType {

    // 视频高度大于宽度
    int HEIGHT_THAN_WIDTH = 0;

    // 视频宽度大于高度，宽高比小于 16/9
    int WIDTH_THAN_HEIGHT = 1;

    // 视频宽度大于高度，且宽高比大于 16/9
    int WIDTH_THAN_MIN_HEIGHT = 2;
}