package com.example.wyyu.gitsamlpe.framework.message;

/**
 * Created by wyyu on 2017/12/29.
 **/

public interface MsgReceiver {

    void onReceiveMessage(MsgType msgType, Message message);
}
