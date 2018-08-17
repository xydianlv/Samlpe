package com.example.wyyu.gitsamlpe.test.floatview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.dialog.SDAlertDialog;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.util.download.DownloadObservable;

/**
 * Created by wyyu on 2018/6/28.
 **/

public class ActivityFloatTest extends ToolbarActivity {

    private Handler handler;
    private int value;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_test);

        initFloatTest();
    }

    @SuppressLint("HandlerLeak") private void initFloatTest() {

        initToolbar("FloatTest", 0xffffffff, 0xff84919b);

        findViewById(R.id.float_button).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                DownloadObservable.getObservable().addNewProgress(0, 400);
                handler.sendEmptyMessageDelayed(0, 1000);
            }
        });

        findViewById(R.id.float_button).setOnLongClickListener(new View.OnLongClickListener() {
            @Override public boolean onLongClick(View v) {
                ActivityTypeface.open(ActivityFloatTest.this);
                return true;
            }
        });

        findViewById(R.id.float_dialog).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                new SDAlertDialog.Builder(ActivityFloatTest.this).show();
            }
        });

        handler = new Handler() {
            @Override public void handleMessage(Message msg) {
                if (msg.what != 0 || value > 400) {
                    DownloadObservable.getObservable().removeProgress(0);
                    handler.removeMessages(0);
                    return;
                }
                value = value + 10;
                DownloadObservable.getObservable().refreshProgress(0, value);
                handler.sendEmptyMessageDelayed(0, 1000);
            }
        };
        value = 0;
    }
}
