package com.example.wyyu.gitsamlpe.framework.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

/**
 * Created by wyyu on 2018/6/28.
 **/

public class TestService extends Service {

    //public TestService() {
    //    super("TestService");
    //    Log.e("TestService", "TestService no value");
    //}
    //
    ///**
    // * Creates an IntentService.  Invoked by your subclass's constructor.
    // *
    // * @param name Used to name the worker thread, important only for debugging.
    // */
    //public TestService(String name) {
    //    super(name);
    //    Log.e("TestService", "TestService");
    //}

    @Override public void onCreate() {
        super.onCreate();
        Log.e("TestService", "onCreate");
    }

    @Override public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e("TestService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e("TestService", "onStart");
    }

    //@Override protected void onHandleIntent(@Nullable Intent intent) {
    //    Log.e("TestService", "onHandleIntent");
    //}

    @Override public void onDestroy() {
        super.onDestroy();
        Log.e("TestService", "onDestroy");
    }

    @Nullable @Override public IBinder onBind(Intent intent) {
        return null;
    }
}
