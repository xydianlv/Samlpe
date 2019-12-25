package com.example.wyyu.gitsamlpe.framework;

import android.os.Handler;
import android.os.Message;

/**
 * Created by wyyu on 2019-12-25.
 **/

public class WeakHandler extends Handler {

    private HandlerCallBack callBack;
    // 这个地方用了 WeakPreference 之后，左右切换就会把 CallBack 回收掉，没法再传回来时间
    //private WeakReference<HandlerCallBack> weakReference;

    public WeakHandler(HandlerCallBack callback) {
        //weakReference = new WeakReference<>(callback);
        this.callBack = callback;
    }

    @Override public void handleMessage(Message msg) {
        //if (weakReference != null && weakReference.get() != null) {
        //    weakReference.get().handlerMessage(msg);
        //}
        if (callBack != null) {
            callBack.handlerMessage(msg);
        }
    }

    public interface HandlerCallBack {

        void handlerMessage(Message msg);
    }
}
