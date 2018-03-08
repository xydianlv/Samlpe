package com.example.wyyu.gitsamlpe.test.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;

/**
 * Created by wyyu on 2018/1/9.
 **/

class RecorderParameter {

    // 每个采样所需的 bit 数
    private int bitPerSample;
    // 采样率
    private int sampleRate;
    // 声道数
    private int channel;

    RecorderParameter() {

        bitPerSample = 16;
        sampleRate = 44100;
        channel = 2;
    }

    RecorderParameter(int channel, int sampleRate, int bitPerSample) {

        this.bitPerSample = bitPerSample;
        this.sampleRate = sampleRate;
        this.channel = channel;
    }

    // 每秒所需字节数
    int getBytePerSecond() {
        return sampleRate * channel * bitPerSample / 8;
    }

    // 每个采样需要的字节数
    int getBlockAlign() {
        return channel * bitPerSample / 8;
    }

    // 缓冲区大小
    int getBufferSize() {
        return AudioRecord.getMinBufferSize(sampleRate, getChannelConfig(), getAudioFormat());
    }

    int getChannelConfig() {

        int channelConfig = AudioFormat.CHANNEL_IN_DEFAULT;
        if (channel == 1) {
            channelConfig = AudioFormat.CHANNEL_IN_MONO;
        } else if (channel == 2) {
            channelConfig = AudioFormat.CHANNEL_IN_STEREO;
        }

        return channelConfig;
    }

    int getAudioFormat() {

        int audioFormat = AudioFormat.ENCODING_DEFAULT;
        if (bitPerSample == 8) {
            audioFormat = AudioFormat.ENCODING_PCM_8BIT;
        } else if (bitPerSample == 16) {
            audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        }

        return audioFormat;
    }

    int getBitPerSample() {
        return bitPerSample;
    }

    int getSampleRate() {
        return sampleRate;
    }

    int getChannel() {
        return channel;
    }
}
