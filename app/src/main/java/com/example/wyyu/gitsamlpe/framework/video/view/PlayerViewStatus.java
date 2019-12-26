package com.example.wyyu.gitsamlpe.framework.video.view;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wyyu on 2019-12-26.
 **/

@IntDef({
    PlayerViewStatus.STATIC, PlayerViewStatus.LOADING, PlayerViewStatus.PLAYING_SHOW_ICON,
    PlayerViewStatus.PLAYING_HIDE_ICON, PlayerViewStatus.PAUSE_SHOW_ICON, PlayerViewStatus.PAUSE_HIDE_ICON,
    PlayerViewStatus.COMPLETE
}) @Retention(RetentionPolicy.SOURCE) public @interface PlayerViewStatus {

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
