package com.example.wyyu.gitsamlpe.test.audio.recorder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2018/1/9.
 **/

public class ActivityAudioRecorder extends AppCompatActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityAudioRecorder.class));
    }

    private static final int LIMITATION_SECONDS = 30;
    private AudioRecorder audioRecorder;
    private boolean isRecording;

    private ImageView resetButton;
    private ImageView playButton;
    private ImageView mainButton;
    private TextView timeInfo;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_recorder);

        initAudioRecorder();
    }

    private void initAudioRecorder() {

        initBasicView();

        initBasicValue();

        initClickView();
    }

    private void initBasicView() {

        timeInfo = findViewById(R.id.time_info);

        resetButton = findViewById(R.id.bt_reset);
        playButton = findViewById(R.id.bt_play);
        mainButton = findViewById(R.id.bt_main);

        findViewById(R.id.test_bt).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startTimeShow();
            }
        });
    }

    private void startTimeShow() {

        // initial - 延迟指定时间开始执行，period - 相隔指定时长执行一次，take - 执行总次数
        Observable<Integer> observableT = Observable.interval(2, 1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(6)
            .map(new Func1<Long, Integer>() {
                @Override public Integer call(Long aLong) {
                    return aLong.intValue();
                }
            });

        // 开始执行
        observableT.doOnSubscribe(new Action0() {
            @Override public void call() {

            }
        }).subscribe(new Observer<Integer>() {
            @Override public void onCompleted() {

            }

            @Override public void onError(Throwable e) {

            }

            @Override public void onNext(Integer integer) {

            }
        });
    }

    private void initBasicValue() {

        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.RECORD_AUDIO },
            1);

        audioRecorder = new AudioRecorder();

        isRecording = false;
    }

    private String getTimeInfo(int seconds) {

        String preInfo = seconds < 10 ? "0" + seconds : "" + seconds;

        return preInfo + "\"/" + LIMITATION_SECONDS + "\"";
    }

    private void initClickView() {

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

                resetButton.setVisibility(View.INVISIBLE);
                playButton.setVisibility(View.INVISIBLE);

                timeInfo.setText(getTimeInfo(0));
                audioRecorder.reset();
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

            }
        });

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                funMainButtonClick();
            }
        });
    }

    private void funMainButtonClick() {

        if (isRecording) {
            mainButton.setImageResource(R.mipmap.audio_start);
            resetButton.setVisibility(View.VISIBLE);
            playButton.setVisibility(View.VISIBLE);
            audioRecorder.pause();
            audioRecorder.getAudioFilePath().subscribe(new Action1<String>() {
                @Override public void call(String s) {

                }
            });
        } else {
            mainButton.setImageResource(R.mipmap.audio_pause);
            resetButton.setVisibility(View.INVISIBLE);
            playButton.setVisibility(View.INVISIBLE);
            audioRecorder.start();
        }
        isRecording = !isRecording;
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        audioRecorder.release();
    }
}
