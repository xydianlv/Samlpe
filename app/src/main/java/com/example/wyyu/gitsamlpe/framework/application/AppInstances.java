package com.example.wyyu.gitsamlpe.framework.application;

import com.facebook.imagepipeline.core.DefaultExecutorSupplier;

/**
 * Created by wyyu on 2018/8/17.
 **/

public class AppInstances {

    private static DefaultExecutorSupplier poolExecutor;

    public static DefaultExecutorSupplier getPoolExecutor() {
        if (poolExecutor == null) {
            poolExecutor = new DefaultExecutorSupplier(Runtime.getRuntime().availableProcessors());
        }
        return poolExecutor;
    }
}
