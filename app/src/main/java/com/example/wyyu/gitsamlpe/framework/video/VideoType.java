package com.example.wyyu.gitsamlpe.framework.video;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wyyu on 2019-12-26.
 **/

@IntDef({
    VideoType.HEIGHT_THAN_WIDTH, VideoType.WIDTH_THAN_HEIGHT, VideoType.WIDTH_THAN_MIN_HEIGHT
}) @Retention(RetentionPolicy.SOURCE) public @interface VideoType {

    // 视频高度大于宽度
    int HEIGHT_THAN_WIDTH = 0;

    // 视频宽度大于高度，宽高比小于 16/9
    int WIDTH_THAN_HEIGHT = 1;

    // 视频宽度大于高度，且宽高比大于 16/9
    int WIDTH_THAN_MIN_HEIGHT = 2;
}
