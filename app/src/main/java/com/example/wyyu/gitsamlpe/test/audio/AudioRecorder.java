package com.example.wyyu.gitsamlpe.test.audio;

import android.annotation.SuppressLint;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import com.example.wyyu.gitsamlpe.util.file.FileManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2018/1/9.
 * AudioRecord 操作类
 **/

public class AudioRecorder implements IAudioRecorder {

    private static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    private static final int DEFAULT_LIMIT = 300;
    private static final int MSG_HANDLER = 0;

    private static final String OUT_FOLDER_PATH = FileManager.getFileManager().getAudioDir();
    private static final String OUT_FILE_NAME = "output.wav";

    // Recorder 的状态监听器
    private OnRecorderListener recorderListener;
    // Recorder 初始化所需数据集合
    private RecorderParameter recorderParameter;
    // Android 提供的 AudioRecord
    private AudioRecord audioRecord;
    // 使用 Handler-Massage 控制录制
    private Handler recordHandler;

    // 录制器当前状态
    private RecorderStatus presentStatus;
    // 是否停止录制
    private AtomicBoolean stopRecording;
    // 时长限制，默认5分钟
    private int limitedSeconds;

    // 由暂停操作产生的音频片段路径集合
    private List<String> filePathList;
    // 合成后的音频文件的路径
    private String outputFilePath;
    // 所有的音频片段已经合成
    private boolean hasMerged;

    // 已经录制的音频时长
    private long recordingTime;
    // 开始时刻，辅助参数
    private long startTime;
    // 辅助参数
    private long funTime;

    AudioRecorder() {

        initBasicValue();

        initAudioRecord();

        initRecordHandler();
    }

    private void initBasicValue() {

        outputFilePath = new File(OUT_FOLDER_PATH, OUT_FILE_NAME).getAbsolutePath();
        filePathList = new LinkedList<>();
        hasMerged = false;

        presentStatus = RecorderStatus.PREPARE;
        limitedSeconds = DEFAULT_LIMIT;

        stopRecording = new AtomicBoolean();
        stopRecording.set(true);

        recordingTime = 0;
        startTime = 0;
        funTime = 0;
    }

    private void initAudioRecord() {

        recorderParameter = new RecorderParameter();

        audioRecord = new AudioRecord(AUDIO_SOURCE, recorderParameter.getSampleRate(),
            recorderParameter.getChannelConfig(), recorderParameter.getAudioFormat(),
            recorderParameter.getBufferSize());
    }

    @SuppressLint("HandlerLeak") private void initRecordHandler() {

        recordHandler = new Handler() {
            @Override public void handleMessage(Message msg) {

                funTime = System.currentTimeMillis() - startTime;

                if (stopRecording.get() || recordingTime + funTime >= limitedSeconds) {
                    if (recorderListener != null) {
                        recorderListener.onStatusChange(RecorderStatus.FILLED);
                    }
                    return;
                }
                recordHandler.sendMessageDelayed(recordHandler.obtainMessage(MSG_HANDLER), 1000);
            }
        };
    }

    @Override public void start() {

        recordHandler.sendMessageDelayed(recordHandler.obtainMessage(MSG_HANDLER), 1000);

        audioRecord.startRecording();
        hasMerged = false;

        presentStatus = RecorderStatus.RECORDING;
        stopRecording.set(false);

        writeDataToFile().subscribe(new Action1<String>() {
            @Override public void call(String s) {

            }
        });

        if (recorderListener != null) {
            recorderListener.onStatusChange(presentStatus);
        }
    }

    private Observable<String> writeDataToFile() {

        return Observable.unsafeCreate(new Observable.OnSubscribe<String>() {
            @Override public void call(Subscriber<? super String> subscriber) {
                byte[] audioData = new byte[recorderParameter.getBufferSize()];
                FileOutputStream outputStream = null;

                try {
                    outputStream = new FileOutputStream(getCacheFile());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                if (outputStream == null) return;

                while (!stopRecording.get()) {
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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private File getCacheFile() {

        File cacheFile = new File(OUT_FOLDER_PATH, "" + System.currentTimeMillis() + ".pcm");

        filePathList.add(cacheFile.getAbsolutePath());

        return cacheFile;
    }

    @Override public void pause() {

        presentStatus = RecorderStatus.PAUSE;
        stopRecording.set(true);

        audioRecord.stop();

        if (recorderListener != null) {
            recorderListener.onStatusChange(presentStatus);
        }
    }

    @Override public void reset() {

        presentStatus = RecorderStatus.PREPARE;

        if (!stopRecording.get()) {
            stopRecording.set(true);
            audioRecord.stop();
        }

        clearLocalCache();

        if (recorderListener != null) {
            recorderListener.onStatusChange(presentStatus);
        }
    }

    private void clearLocalCache() {

        if (filePathList == null || filePathList.isEmpty()) return;

        for (String filePath : filePathList) {
            new File(filePath).delete();
        }

        filePathList.clear();
    }

    @Override public void release() {

        if (!stopRecording.get()) {
            pause();
        }

        clearLocalCache();

        if (audioRecord != null) {
            audioRecord.release();
            audioRecord = null;
        }

        if (recordHandler != null) {
            recordHandler.removeMessages(MSG_HANDLER);
        }
    }

    @Override public void setOnRecorderListener(OnRecorderListener recorderListener) {
        this.recorderListener = recorderListener;
    }

    @Override public void setTimeLimitation(int seconds) {
        this.limitedSeconds = seconds;
    }

    @Override public Observable<String> getAudioFilePath() {
        return Observable.unsafeCreate(new Observable.OnSubscribe<String>() {
            @Override public void call(Subscriber<? super String> subscriber) {
                if (hasMerged) {
                    subscriber.onNext(outputFilePath);
                } else if (filePathList.isEmpty()) {
                    subscriber.onError(new Throwable("录制失败"));
                } else {
                    new PcmToWavUtil(recorderParameter).pcmToWav(filePathList.get(0), outputFilePath);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
