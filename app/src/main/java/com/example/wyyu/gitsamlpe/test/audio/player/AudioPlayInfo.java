package com.example.wyyu.gitsamlpe.test.audio.player;

/**
 * Created by wyyu on 2018/10/10.
 **/

public class AudioPlayInfo {

    // 音频文件 ID
    public long audioId;

    // 音频文件地址
    public String path;

    public AudioPlayInfo(long audioId, String path) {
        this.audioId = audioId;
        this.path = path;
    }
}
