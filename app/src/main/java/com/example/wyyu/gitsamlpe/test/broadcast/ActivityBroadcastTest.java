package com.example.wyyu.gitsamlpe.test.broadcast;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.pagedialog.PageDialog;

/**
 * Created by wyyu on 2018/10/17.
 **/

public class ActivityBroadcastTest extends ToolbarActivity implements View.OnClickListener {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityBroadcastTest.class));
    }

    private ScanReceiver scanReceiver;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_test);

        initActivity();
    }

    private void initActivity() {

        initToolbar("BroadCast");

        findViewById(R.id.broad_test_dialog).setOnClickListener(this);
        findViewById(R.id.broad_test_bt).setOnClickListener(this);

        scanReceiver = new ScanReceiver();
        registerReceiver(scanReceiver, new IntentFilter(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE));
    }

    @Override protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(scanReceiver);
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.broad_test_bt:
                sendBroadcast();
                break;
            case R.id.broad_test_dialog:
                showTestDialog();
                break;
        }
    }

    private void sendBroadcast() {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        sendBroadcast(intent);
    }

    private void showTestDialog() {
        PageDialog.show(this);
    }
}
