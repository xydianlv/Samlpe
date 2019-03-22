package com.example.wyyu.gitsamlpe.test.livedata;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by wyyu on 2019/3/20.
 **/

public class SeekBarViewModel extends ViewModel {

    public MutableLiveData<Integer> seekBarValue = new MutableLiveData<>();

    public SeekBarViewModel() {

    }
}
