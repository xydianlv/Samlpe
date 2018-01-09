package com.example.wyyu.gitsamlpe.test.audio;

/**
 * Created by wyyu on 2018/1/9.
 **/

public interface IAudioRecorder {

    enum RecorderStatus {

        // 三种状态 ：准备、录制、暂停
        PREPARE, RECORD, PAUSE,
    }

    void start();

    void pause();

    void reset();

    void release();

    void setOnRecorderListener(OnRecorderListener recorderListener);

    // 设置录取时间限制
    void setTimeLimitation(int seconds);

    interface OnRecorderListener {

        // 视频录制时，实时向外报告当前录制时间，单位为 "秒"
        void onRecording(int seconds);

        // 暂停后，传出可播放音频文件地址
        void onPause(String filePath);
    }
}
