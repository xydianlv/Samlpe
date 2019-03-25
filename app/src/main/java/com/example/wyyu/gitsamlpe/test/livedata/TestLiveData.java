package com.example.wyyu.gitsamlpe.test.livedata;

import android.arch.lifecycle.LiveData;
import android.util.Log;

/**
 * Created by wyyu on 2019/3/25.
 **/

@SuppressWarnings("WeakerAccess")
public class TestLiveData<T> extends LiveData<T> {

    @Override public void postValue(T value) {
        super.postValue(value);
    }

    @Override public void setValue(T value) {
        super.setValue(value);
    }

    @Override public void onActive() {
        super.onActive();

        Log.e("LiveDataTest", "onActive");
    }

    @Override public void onInactive() {
        super.onInactive();

        Log.e("LiveDataTest", "onInactive");
    }
}
