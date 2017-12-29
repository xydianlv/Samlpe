package com.example.wyyu.gitsamlpe.test;

import android.content.Context;
import android.content.Intent;

import com.example.wyyu.gitsamlpe.test.location.ActivityLocation;
import com.example.wyyu.gitsamlpe.test.lock.finger.ActivityFingerLockTest;
import com.example.wyyu.gitsamlpe.test.lock.gesture.ActivityGestureLockTest;
import com.example.wyyu.gitsamlpe.test.recyclerview.ActivityFastScrollTest;

/**
 * Created by wyyu on 2017/12/28.
 **/

class UIShowManager {

    static void startGestureLockActivity(Context context) {
        startTargetActivityFromMain(context, ActivityGestureLockTest.class);
    }

    static void startFingerLockActivity(Context context) {
        startTargetActivityFromMain(context, ActivityFingerLockTest.class);
    }

    static void startFastRecyclerActivity(Context context) {
        startTargetActivityFromMain(context, ActivityFastScrollTest.class);
    }

    static void startLocationActivity(Context context) {
        startTargetActivityFromMain(context, ActivityLocation.class);
    }

    private static void startTargetActivityFromMain(Context context, Class targetActivity) {
        context.startActivity(new Intent(context, targetActivity));
    }

}
