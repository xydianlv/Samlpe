package com.example.wyyu.gitsamlpe.test.floatview;

import android.app.Service;

/**
 * Created by wyyu on 2019/1/3.
 **/

public interface IKeepLiveManager {

    void init();

    void startKeepLiveActivity();

    void finishKeepLiveActivity();

    void setForeground(Service keepLiveService, Service innerService);
}
