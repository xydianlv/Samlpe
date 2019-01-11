package com.example.wyyu.gitsamlpe.test.live;

/**
 * Created by wyyu on 2019/1/8.
 **/

public interface IKeepLiveManager {

    void init();

    void startBackService();

    void closeBackService();

    void showFloatView();

    void hideFloatView();

    void openCloseListener();

    void closeCloseListener();

    void showNotifyBar();

    void hideNotifyBar();
}
