package com.example.wyyu.gitsamlpe.framework.volume;

import android.app.Activity;
import androidx.annotation.NonNull;

/**
 * Created by wyyu on 2018/8/11.
 **/

public interface IVolumeObservable {

    // 点击音量降低键
    void onClickVolumeDown();

    // 点击音量提升键
    void onClickVolumeUp();

    // 当系统音量发生变化，通过 BroadCastReceiver 传递给 Observable
    void onVolumeValueChange();

    // 滑动 SeekBar 刷新音量 Progress
    void refreshProgress(int progress);

    // 绑定 Activity 到 Observable
    void attachActivity(@NonNull Activity activity);

    // 解除 Activity 到 Observable
    void detachActivity(@NonNull Activity activity);

    // 释放单例
    void release();
}
