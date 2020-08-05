package com.example.wyyu.gitsamlpe.test.video.widget;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wyyu on 2020-07-28.
 **/

@IntDef({
    CMPlayerStatus.STATIC, CMPlayerStatus.LOADING, CMPlayerStatus.PLAYING_SHOW_ICON,
    CMPlayerStatus.PLAYING_HIDE_ICON, CMPlayerStatus.PAUSE_SHOW_ICON, CMPlayerStatus.PAUSE_HIDE_ICON,
    CMPlayerStatus.COMPLETE
}) @Retention(RetentionPolicy.SOURCE) public @interface CMPlayerStatus {

    // 初始化封面状态
    int STATIC = 0;

    // 加载状态
    int LOADING = 1;

    // 播放中，展示播放按钮
    int PLAYING_SHOW_ICON = 20;

    // 播放中，隐藏播放按钮
    int PLAYING_HIDE_ICON = 21;

    // 暂停中，展示播放按钮
    int PAUSE_SHOW_ICON = 30;

    // 暂停中，隐藏播放按钮
    int PAUSE_HIDE_ICON = 31;

    // 播放完结
    int COMPLETE = 4;
}