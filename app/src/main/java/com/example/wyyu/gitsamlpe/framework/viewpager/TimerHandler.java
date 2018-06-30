package com.example.wyyu.gitsamlpe.framework.viewpager;

import android.os.Handler;
import android.os.Message;
import android.util.SparseIntArray;

/**
 * Created by wyyu on 2018/6/29.
 **/

public class TimerHandler extends Handler {

    public interface TimerHandlerListener {
        int getNextItem();
        void callBack();
    }

    SparseIntArray specialInterval;
    long interval;
    boolean isStopped = true;
    TimerHandlerListener listener;

    static final int MSG_TIMER_ID = 87108;

    public TimerHandler(TimerHandlerListener listener, long interval) {
        this.listener = listener;
        this.interval = interval;
    }

    @Override
    public void handleMessage(Message msg) {
        if (MSG_TIMER_ID == msg.what) {
            if (listener != null) {
                int nextIndex = listener.getNextItem();
                listener.callBack();
                tick(nextIndex);
            }
        }
    }

    public void tick(int index) {
        sendEmptyMessageDelayed(TimerHandler.MSG_TIMER_ID, getNextInterval(index));
    }

    private long getNextInterval(int index) {
        long next = interval;
        if (specialInterval != null) {
            long has = specialInterval.get(index, -1);
            if (has > 0) {
                next = has;
            }
        }
        return next;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void setStopped(boolean stopped) {
        isStopped = stopped;
    }

    public void setListener(TimerHandlerListener listener) {
        this.listener = listener;
    }

    public void setSpecialInterval(SparseIntArray specialInterval) {
        this.specialInterval = specialInterval;
    }
}