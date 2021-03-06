package com.example.wyyu.gitsamlpe.test.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.SeekBar;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2018/5/7.
 **/

public class ActivityProgressTest extends ToolbarActivity {

    private static final int HANDLER_MESSAGE = 0;
    private static final int MAX_NUMBER = 5;

    private ProgressContainer progressContainer;
    private Handler handler;

    private ProgressDrawable progressDrawable;
    private PercentProgress percentProgress;

    private int progress;
    private int index;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_test);

        initProgressTest();
    }

    private void initProgressTest() {
        initToolbar("Progress", 0xffffffff, 0xff84919b);
        initBasicValue();
        initBasicView();
        initPercentDrawable();

        startProgress();
    }

    @SuppressLint("HandlerLeak") private void initBasicValue() {
        handler = new Handler() {
            public void handleMessage(Message msg) {
                if (progress >= 100) {
                    progress = 0;
                    index++;
                }
                if (index > 5) {
                    handler.removeMessages(HANDLER_MESSAGE);
                    progressContainer.setVisibility(View.GONE);
                    return;
                }
                progress = progress + 20;
                progressContainer.refreshProgressInfo(index, MAX_NUMBER);
                progressContainer.refreshProgressPercent(progress);

                progressDrawable.onLevelChange(progress);

                handler.sendEmptyMessageDelayed(HANDLER_MESSAGE, 1000);
            }
        };
    }

    private void initBasicView() {
        progressContainer = findViewById(R.id.progress_container);
        percentProgress = findViewById(R.id.percent_progress);
        SeekBar seekBar = findViewById(R.id.percent_seek_bar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percentProgress.refreshProgress(progress);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initPercentDrawable() {
        progressDrawable = new ProgressDrawable();
        findViewById(R.id.percent_drawable_test).setBackground(progressDrawable);
    }

    private void startProgress() {

        progress = 0;
        index = 1;

        progressContainer.refreshProgressInfo(index, MAX_NUMBER);
        progressContainer.refreshProgressPercent(progress);

        handler.sendEmptyMessageDelayed(HANDLER_MESSAGE, 1000);
    }
}
