package com.example.wyyu.gitsamlpe.test.audio;

import android.media.AudioRecord;
import android.media.MediaRecorder;
import com.example.wyyu.gitsamlpe.framework.ULog;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wyyu on 2018/1/9.
 * AudioRecord 操作类
 **/

public class AudioRecorder implements IAudioRecorder {

    private static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;

    private OnRecorderListener recorderListener;
    private RecorderParameter recorderParameter;

    private RecorderStatus presentStatus;
    private AudioRecord audioRecord;

    private TimerTask timerTask;
    private Timer timer;

    private int limitedSeconds;
    private int time;

    private static class AudioRecorderHolder {
        private static AudioRecorder audioRecorder = new AudioRecorder();
    }

    static AudioRecorder getInstance() {
        return AudioRecorderHolder.audioRecorder;
    }

    private AudioRecorder() {

        initBasicValue();

        initAudioRecord();
    }

    private void initBasicValue() {

        presentStatus = RecorderStatus.PREPARE;
        limitedSeconds = 0;
        time = 0;

        recorderParameter = new RecorderParameter();
    }

    private void initAudioRecord() {

        audioRecord = new AudioRecord(AUDIO_SOURCE, recorderParameter.getSampleRate(),
            recorderParameter.getChannelConfig(), recorderParameter.getAudioFormat(),
            recorderParameter.getBufferSize());
    }

    @Override public void start() {

        time = presentStatus.equals(RecorderStatus.PREPARE) ? 0 : time;
        timerTask = new TimerTask() {
            @Override public void run() {
                if (limitedSeconds == 0 || limitedSeconds <= time) {
                    timerCancel();
                    return;
                }
                recorderListener.onRecording(time++);
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, time, 1000);

        ULog.show("AudioRecorder -> state" + audioRecord.getState());
        audioRecord.startRecording();
        writeDataToFile();

        presentStatus = RecorderStatus.RECORD;
    }

    private void timerCancel() {
        timerTask.cancel();
        timer.cancel();
    }

    private void writeDataToFile() {

        byte[] audioData = new byte[recorderParameter.getBufferSize()];
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(UtilFile.getCacheFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (outputStream == null) return;

        while (presentStatus.equals(RecorderStatus.RECORD)) {
            audioRecord.read(audioData, 0, recorderParameter.getBufferSize());
            try {
                outputStream.write(audioData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override public void pause() {

        timerCancel();

        presentStatus = RecorderStatus.PAUSE;
    }

    @Override public void reset() {

        timerCancel();

        presentStatus = RecorderStatus.PREPARE;
    }

    @Override public void release() {
        timerCancel();

        if (audioRecord != null) {
            audioRecord.release();
            audioRecord = null;
        }
    }

    @Override public void setOnRecorderListener(OnRecorderListener recorderListener) {
        this.recorderListener = recorderListener;
    }

    @Override public void setTimeLimitation(int seconds) {
        this.limitedSeconds = seconds;
    }
}
