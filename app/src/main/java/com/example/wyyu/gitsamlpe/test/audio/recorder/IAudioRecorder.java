package com.example.wyyu.gitsamlpe.test.audio.recorder;

import rx.Observable;

/**
 * Created by wyyu on 2018/1/9.
 **/

public interface IAudioRecorder {

    enum RecorderStatus {

        // 三种状态 ：准备、录制、暂停、到达时限
        PREPARE, RECORDING, PAUSE, FILLED
    }

    // 开始
    void start();

    // 暂停
    void pause();

    // 重置
    void reset();

    // 释放
    void release();

    // 设置录制监听
    void setOnRecorderListener(OnRecorderListener recorderListener);

    // 设置录取时间限制，单位为秒
    void setTimeLimitation(int seconds);

    // 获取合成后的音频文件路径
    Observable<String> getAudioFilePath();

    interface OnRecorderListener {

        // 向外抛出录制状态
        void onStatusChange(RecorderStatus recorderStatus);

        // 视频录制时，实时向外报告当前录制时间，单位为 "秒"
        void onRecording(int seconds);
    }
}
