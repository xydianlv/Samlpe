package com.example.wyyu.gitsamlpe.test.livedata;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.SystemClock;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wyyu on 2019/3/20.
 **/

public class LiveDataTimerViewModel extends ViewModel {

    private static final int ONE_SECOND = 1000;

    private MutableLiveData<Long> elapsedTime = new MutableLiveData<>();

    private long mInitialTime;

    public LiveDataTimerViewModel() {

        mInitialTime = SystemClock.elapsedRealtime();
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override public void run() {
                long newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000;
                elapsedTime.postValue(newValue);
            }
        }, ONE_SECOND, ONE_SECOND);
    }

    public LiveData<Long> getElapsedTime() {
        return elapsedTime;
    }
}
