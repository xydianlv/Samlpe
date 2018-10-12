package com.example.wyyu.gitsamlpe.test.audio.player;

import android.support.annotation.NonNull;

/**
 * Created by wyyu on 2018/9/20.
 *
 * 一个超级简单仅用于聊天室的音频播放器
 *
 * 传入一个含有 id、path 的数据结构
 *
 * id 做播放控制
 * path 音频数据来源，或本地地址或服务器链接
 *
 * 那种一组音频文件的播放顺序控制，另起一个类来做控制，保证 AudioPlayer 只做播放相关的逻辑
 **/

public interface IAudioPlayer {

    /**
     * 无论是点击播放还是点击暂停，只需要调用这个接口即可
     *
     * 当播放器处于闲置状态时，调用 play 接口时启动 Player 并开始播放
     *
     * 当播放器处于播放状态时，需要做一个判断
     * * * 若传入的 Audio-id 与当前正在播放的一致，停止播放并释放播放器
     * * * 若传入的 Audio-id 与当前正在播放的不一致，停止当前播放行为，更换播放数据重新播放
     *
     * 播放器播放完一段音频文件后自动停止播放
     *
     * @param playInfo 待播放的音频数据
     */
    void play(@NonNull AudioPlayInfo playInfo);

    /**
     * 手动释放掉播放器
     */
    void release();

    /**
     * 手动停止音频播放器，用于退出播放界面时的手动调用
     */
    void stop();
}
