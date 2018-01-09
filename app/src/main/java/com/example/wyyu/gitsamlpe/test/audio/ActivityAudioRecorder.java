package com.example.wyyu.gitsamlpe.test.audio;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2018/1/9.
 **/

public class ActivityAudioRecorder extends FullScreenActivity {

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
    }

    private void initBasicValue() {

        ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.RECORD_AUDIO}, 1);

        audioRecorder = AudioRecorder.getInstance();

        audioRecorder.setTimeLimitation(LIMITATION_SECONDS);

        audioRecorder.setOnRecorderListener(new IAudioRecorder.OnRecorderListener() {
            @Override public void onRecording(final int seconds) {
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        timeInfo.setText(getTimeInfo(seconds));
                    }
                });
            }

            @Override public void onPause(String filePath) {

            }
        });

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
