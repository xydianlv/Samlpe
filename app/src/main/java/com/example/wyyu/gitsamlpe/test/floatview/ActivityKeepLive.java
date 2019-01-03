package com.example.wyyu.gitsamlpe.test.floatview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import com.example.wyyu.gitsamlpe.framework.message.Message;
import com.example.wyyu.gitsamlpe.framework.message.MsgReceiver;
import com.example.wyyu.gitsamlpe.framework.message.MsgSender;
import com.example.wyyu.gitsamlpe.framework.message.MsgType;

/**
 * Created by wyyu on 2019/1/3.
 **/

public class ActivityKeepLive extends AppCompatActivity implements MsgReceiver {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityKeepLive.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setGravity(51);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.width = 1;
        layoutParams.height = 1;
        window.setAttributes(layoutParams);

        MsgSender.getMsgSender().attach(this);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        MsgSender.getMsgSender().detach(this);
    }

    @Override public void onReceiveMessage(MsgType msgType, Message message) {
        if (msgType.equals(MsgType.FINISH_KEEP_LIVE)) {
            finish();
        }
    }
}
