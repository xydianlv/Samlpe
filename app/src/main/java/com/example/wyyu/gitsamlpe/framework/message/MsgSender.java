package com.example.wyyu.gitsamlpe.framework.message;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wyyu on 2017/12/29.
 **/

public class MsgSender {

    private List<MsgReceiver> msgReceiverList;

    private MsgSender() {
        msgReceiverList = new LinkedList<>();
    }

    private static class MsgSenderHolder {
        private static MsgSender msgSender = new MsgSender();
    }

    public static MsgSender getMsgSender() {
        return MsgSenderHolder.msgSender;
    }

    public void attach(MsgReceiver msgReceiver) {
        msgReceiverList.add(msgReceiver);
    }

    public void detach(MsgReceiver msgReceiver) {
        msgReceiverList.remove(msgReceiver);
    }

    // sendMessage 操作只能在 UI线程 中进行
    public void sendMessage(MsgType msgType, Message message) {
        for (MsgReceiver msgReceiver : msgReceiverList) {
            msgReceiver.onReceiveMessage(msgType, message);
        }
    }

}
