package com.example.wyyu.gitsamlpe.test.svga;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wyyu on 2019-12-25.
 **/

public enum SvgAExecutor {

    INSTANCE;

    private ExecutorService mExecutor;

    SvgAExecutor() {
        mExecutor = Executors.newSingleThreadExecutor();
    }

    public void execute(Runnable runnable) {
        if (mExecutor.isShutdown()) {
            mExecutor = Executors.newSingleThreadExecutor();
        }
        mExecutor.execute(runnable);
    }

    public void terminate() {
        mExecutor.shutdown();
    }}
