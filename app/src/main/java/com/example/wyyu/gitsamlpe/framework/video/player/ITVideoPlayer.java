package com.example.wyyu.gitsamlpe.framework.video.player;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import android.view.TextureView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.LinkedHashMap;

/**
 * Created by wyyu on 2019-12-26.
 **/

public interface ITVideoPlayer {

    /**
     * 播放器状态
     */
    @IntDef({
        MediaStatus.PREPARE, MediaStatus.LOADING, MediaStatus.PLAYING, MediaStatus.PAUSE,
        MediaStatus.COMPLETE, MediaStatus.ERROR
    }) @Retention(RetentionPolicy.SOURCE) @interface MediaStatus {
        // 播放准备
        int PREPARE = 0;
        // 正在加载
        int LOADING = 1;
        // 播放中
        int PLAYING = 2;
        // 播放暂停
        int PAUSE = 3;
        // 播放结束
        int COMPLETE = 4;
        // 播放失败
        int ERROR = 5;
    }

    /**
     * 视频播放状态监听
     */
    interface MediaPlayListener {

        // 播放器当前状态
        void onStateChange(@MediaStatus int status);

        // position : 当前播放时长，单位 毫秒
        // progress : 当前进度百分比，取值 [0,100]
        void onProgress(long position, int progress);
    }

    /**
     * 传入待播放的视频数据，并直接开始视频播放
     *
     * @param textureView 视频播放控件
     * @param sourceMap 视频源列表，key 为换源类型，value 为该源对应的服务器地址
     * @param sourceId 当前播放视频的唯一标识
     * @param playListener 播放状态监听回调
     */
    void setDataAndPlay(@NonNull LinkedHashMap<String, String> sourceMap, long sourceId,
        @NonNull MediaPlayListener playListener, @NonNull TextureView textureView);

    /**
     * 传入音频播放数据
     *
     * @param audioSource 音频数据
     * @param sourceId 数据唯一标识
     * @param playListener 播放状态监听回调
     */
    void setAudioAndPlay(@NonNull String audioSource, long sourceId,
        @NonNull MediaPlayListener playListener);

    /**
     * 设置视频播放监听
     *
     * @param sourceId 当前播放视频的唯一标识
     * @param playListener 播放状态监听回调
     */
    void attachPlayListener(long sourceId, @NonNull MediaPlayListener playListener);

    /**
     * 移除指定的播放监听，放置内存泄漏
     *
     * @param playListener 播放状态监听回调
     */
    void detachPlayListener(@NonNull MediaPlayListener playListener);

    /**
     * 开始播放，与暂停状态相互切换
     */
    void start();

    /**
     * 暂停播放，与播放状态相互切换
     */
    void pause();

    /**
     * 开始播放，与结束状态相互切换
     */
    void reStart();

    /**
     * 结束播放，无关状态，直接停止当前播放行为，置播放器到准备接收下一次播放状态
     */
    void complete();

    /**
     * 释放播放器数据，再次调用需重新初始化，一般用作退至后台或退出应用时调用
     */
    void release();

    /**
     * 设置当前播放进度的百分比
     *
     * @param progress 百分比，取值 [0,100]
     */
    void seekTo(int progress);

    /**
     * 设置是否循环播放
     *
     * @param repeat 是否循环播放
     */
    void setRepeat(boolean repeat);

    /**
     * 设置是否静音
     *
     * @param setMute 是否静音
     */
    void setMute(boolean setMute);

    /**
     * 获取当前的播放进度
     *
     * @return 播放进度
     */
    long getCurrentPosition();

    /**
     * 获取视频时长
     *
     * @return 视频时长
     */
    long getDuration();

    /**
     * 获取当前正在播放的视频ID
     *
     * @return 视频ID
     */
    long getSourceId();
}
