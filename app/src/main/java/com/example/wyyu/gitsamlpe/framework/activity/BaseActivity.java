package com.example.wyyu.gitsamlpe.framework.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.wyyu.gitsamlpe.framework.message.Message;
import com.example.wyyu.gitsamlpe.framework.message.MsgSender;
import com.example.wyyu.gitsamlpe.framework.message.MsgType;
import com.example.wyyu.gitsamlpe.framework.message.MsgCallBack;
import com.example.wyyu.gitsamlpe.framework.message.MsgReceiver;
import com.example.wyyu.gitsamlpe.util.download.DownloadObservable;
import java.util.HashMap;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class BaseActivity extends AppCompatActivity implements MsgReceiver {

    private HashMap<MsgType, MsgCallBack> msgCallBackHashMap;
    private Unbinder unbinder;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBaseData();
    }

    @Override public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        unbinder = ButterKnife.bind(this);
    }

    private void initBaseData() {
        msgCallBackHashMap = new HashMap<>();
        MsgSender.getMsgSender().attach(this);
        DownloadObservable.getObservable().attachActivity(this);
        //VolumeObservable.getObservable().attachActivity(this);

        registerToLiveBus();
    }

    protected void registerToLiveBus() {

    }

    @Override protected void onResume() {
        super.onResume();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        releaseMsgListener();
        DownloadObservable.getObservable().detachActivity(this);
        //VolumeObservable.getObservable().detachActivity(this);
    }

    private void releaseMsgListener() {
        MsgSender.getMsgSender().detach(this);

        msgCallBackHashMap.clear();
        msgCallBackHashMap = null;
    }

    protected void addMessageListener(MsgType msgType, MsgCallBack callBack) {
        msgCallBackHashMap.put(msgType, callBack);
    }

    @Override public void onReceiveMessage(MsgType msgType, Message message) {
        if (msgCallBackHashMap.containsKey(msgType)) {
            msgCallBackHashMap.get(msgType).onMessageCallBack(message);
        }
    }

    @Override public boolean dispatchKeyEvent(KeyEvent event) {
        //if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) {
        //    VolumeObservable.getObservable().onClickVolumeDown();
        //    return true;
        //} else if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP) {
        //    VolumeObservable.getObservable().onClickVolumeUp();
        //    return true;
        //}
        return super.dispatchKeyEvent(event);
    }
}
