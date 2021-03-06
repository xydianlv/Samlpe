package com.example.wyyu.gitsamlpe.test.floatview;

import android.annotation.SuppressLint;
import androidx.lifecycle.Observer;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.dialog.SDAlertDialog;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import com.example.wyyu.gitsamlpe.util.download.DownloadObservable;
import com.jeremyliao.liveeventbus.LiveEventBus;

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

    @Override protected void registerToLiveBus() {
        LiveEventBus.get()
            .with(EventTest.EVENT_KEY, EventTest.class)
            .observe(this, new Observer<EventTest>() {
                @Override public void onChanged(@Nullable EventTest eventTest) {
                    UToast.showShort(ActivityFloatTest.this,
                        String.valueOf(eventTest == null ? 0 : eventTest.number));
                }
            });
        LiveEventBus.get()
            .with(EventCheck.EVENT_KEY, EventCheck.class)
            .observe(this, new Observer<EventCheck>() {
                @Override public void onChanged(@Nullable EventCheck eventCheck) {
                    UToast.showShort(ActivityFloatTest.this,
                        String.valueOf(eventCheck == null ? 0 : eventCheck.number));
                }
            });
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

        findViewById(R.id.float_sys).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                showSysFloatView();
            }
        });

        findViewById(R.id.live_service).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startLiveService();
            }
        });

        findViewById(R.id.bus_test).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                LiveEventBus.get().with(EventTest.EVENT_KEY).setValue(new EventTest(47));
            }
        });

        findViewById(R.id.bus_check).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                LiveEventBus.get().with(EventCheck.EVENT_KEY).setValue(new EventCheck(23));
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

    private void showSysFloatView() {
        startService(new Intent(this, FloatingService.class));
    }

    private void startLiveService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, KeepLiveService.class));
        } else {
            startService(new Intent(this, KeepLiveService.class));
        }
    }
}
