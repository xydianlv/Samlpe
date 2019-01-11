package com.example.wyyu.gitsamlpe.framework.application;

import android.content.Context;
import android.content.SharedPreferences;
import com.facebook.imagepipeline.core.DefaultExecutorSupplier;

/**
 * Created by wyyu on 2018/8/17.
 **/

public class AppInstances {

    private static final String KEY_PREFERENCE = "key_preference";

    private static DefaultExecutorSupplier poolExecutor;
    private static SharedPreferences sharedPreferences;

    public static DefaultExecutorSupplier getPoolExecutor() {
        if (poolExecutor == null) {
            poolExecutor = new DefaultExecutorSupplier(Runtime.getRuntime().availableProcessors());
        }
        return poolExecutor;
    }

    public static SharedPreferences getSharedPreference() {
        if (sharedPreferences == null) {
            sharedPreferences = AppController.getAppContext()
                .getSharedPreferences(KEY_PREFERENCE, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }
}
