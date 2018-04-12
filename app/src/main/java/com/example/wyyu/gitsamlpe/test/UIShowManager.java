package com.example.wyyu.gitsamlpe.test;

import android.content.Context;
import android.content.Intent;
import com.example.wyyu.gitsamlpe.test.audio.ActivityAudioRecorder;
import com.example.wyyu.gitsamlpe.test.bigimage.ActivityImageList;
import com.example.wyyu.gitsamlpe.test.card.ActivityCard;
import com.example.wyyu.gitsamlpe.test.file.ActivityFileListTest;
import com.example.wyyu.gitsamlpe.test.location.ActivityLocation;
import com.example.wyyu.gitsamlpe.test.lock.finger.ActivityFingerLockTest;
import com.example.wyyu.gitsamlpe.test.lock.gesture.ActivityGestureLockTest;
import com.example.wyyu.gitsamlpe.test.recyclerview.ActivityFastScrollTest;
import com.example.wyyu.gitsamlpe.test.slide.ActivitySlide;
import com.example.wyyu.gitsamlpe.test.theme.ActivityThemeList;
import com.example.wyyu.gitsamlpe.test.video.ActivityPlayVideo;

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

    static void startSlideActivity(Context context) {
        startTargetActivityFromMain(context, ActivitySlide.class);
    }

    static void startAudioRecordActivity(Context context) {
        startTargetActivityFromMain(context, ActivityAudioRecorder.class);
    }

    static void startCardActivity(Context context) {
        startTargetActivityFromMain(context, ActivityCard.class);
    }

    static void startFileListActivity(Context context) {
        startTargetActivityFromMain(context, ActivityFileListTest.class);
    }

    static void startVideoPlayActivity(Context context){
        startTargetActivityFromMain(context, ActivityPlayVideo.class);
    }

    static void startImageListActivity(Context context){
        startTargetActivityFromMain(context, ActivityImageList.class);
    }

    static void startThemeListActivity(Context context){
        startTargetActivityFromMain(context, ActivityThemeList.class);
    }

    private static void startTargetActivityFromMain(Context context, Class targetActivity) {
        context.startActivity(new Intent(context, targetActivity));
    }
}
