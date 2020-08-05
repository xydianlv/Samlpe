package com.example.wyyu.gitsamlpe.test.livedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by wyyu on 2019/3/20.
 **/

public class SeekBarViewModel extends ViewModel {

    public MutableLiveData<Integer> seekBarValue = new MutableLiveData<>();

    public SeekBarViewModel() {

    }
}
