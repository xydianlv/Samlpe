package com.example.wyyu.gitsamlpe.framework.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.wyyu.gitsamlpe.framework.message.Message;
import com.example.wyyu.gitsamlpe.framework.message.MsgSender;
import com.example.wyyu.gitsamlpe.framework.message.MsgType;
import com.example.wyyu.gitsamlpe.framework.message.MsgCallBack;
import com.example.wyyu.gitsamlpe.framework.message.MsgReceiver;

import java.util.HashMap;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class BaseActivity extends AppCompatActivity implements MsgReceiver {

    private HashMap<MsgType, MsgCallBack> msgCallBackHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBaseData();
    }

    private void initBaseData() {

        msgCallBackHashMap = new HashMap<>();

        MsgSender.getMsgSender().attach(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMsgListener();
    }

    private void releaseMsgListener() {
        MsgSender.getMsgSender().detach(this);

        msgCallBackHashMap.clear();
        msgCallBackHashMap = null;
    }

    protected void addMessageListener(MsgType msgType, MsgCallBack callBack) {
        msgCallBackHashMap.put(msgType, callBack);
    }

    @Override
    public void onReceiveMessage(MsgType msgType, Message message) {
        if (msgCallBackHashMap.containsKey(msgType)) {
            msgCallBackHashMap.get(msgType).onMessageCallBack(message);
        }
    }
}
