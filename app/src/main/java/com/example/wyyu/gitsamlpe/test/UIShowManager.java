package com.example.wyyu.gitsamlpe.test;

import android.content.Context;
import android.content.Intent;
import com.example.wyyu.gitsamlpe.test.audio.player.ActivityAudioList;
import com.example.wyyu.gitsamlpe.test.audio.recorder.ActivityAudioRecorder;
import com.example.wyyu.gitsamlpe.test.bigimage.ActivityMultiImage;
import com.example.wyyu.gitsamlpe.test.broadcast.ActivityBroadcastTest;
import com.example.wyyu.gitsamlpe.test.card.ActivityCard;
import com.example.wyyu.gitsamlpe.test.card.ActivityNormalCard;
import com.example.wyyu.gitsamlpe.test.database.ActivityDataBase;
import com.example.wyyu.gitsamlpe.test.dialog.ActivityFullDialog;
import com.example.wyyu.gitsamlpe.test.dialog.ActivityProgressTest;
import com.example.wyyu.gitsamlpe.test.dynamic.ActivityDynamicSize;
import com.example.wyyu.gitsamlpe.test.file.ActivityFileListTest;
import com.example.wyyu.gitsamlpe.test.floatview.ActivityFloatTest;
import com.example.wyyu.gitsamlpe.test.fragment.ActivitySlideTest;
import com.example.wyyu.gitsamlpe.test.fresco.ActivityFrescoTest;
import com.example.wyyu.gitsamlpe.test.image.edit.ActivityEditImage;
import com.example.wyyu.gitsamlpe.test.image.shot.ActivityShotScreen;
import com.example.wyyu.gitsamlpe.test.list.ActivityExpandableListView;
import com.example.wyyu.gitsamlpe.test.location.ActivityLocation;
import com.example.wyyu.gitsamlpe.test.lock.finger.ActivityFingerLockTest;
import com.example.wyyu.gitsamlpe.test.lock.gesture.ActivityGestureLockTest;
import com.example.wyyu.gitsamlpe.test.image.matisse.ActivityLocalImage;
import com.example.wyyu.gitsamlpe.test.notify.ActivityNotifyTest;
import com.example.wyyu.gitsamlpe.test.number.ActivityNumber;
import com.example.wyyu.gitsamlpe.test.pager.ActivityPagerTest;
import com.example.wyyu.gitsamlpe.test.permission.ActivityPermissionTest;
import com.example.wyyu.gitsamlpe.test.recyclerview.ActivityFastScrollTest;
import com.example.wyyu.gitsamlpe.test.slide.ActivitySlideT;
import com.example.wyyu.gitsamlpe.test.softkeyboard.ActivitySoftKeyboard;
import com.example.wyyu.gitsamlpe.test.text.ActivityAdapterText;
import com.example.wyyu.gitsamlpe.test.theme.ActivityThemeList;
import com.example.wyyu.gitsamlpe.test.video.ActivityVideoList;
import com.example.wyyu.gitsamlpe.test.volume.ActivityVolume;
import com.example.wyyu.gitsamlpe.test.weibo.ui.MainBlogActivity;

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
        startTargetActivityFromMain(context, ActivitySlideT.class);
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

    //static void startVideoPlayActivity(Context context) {
    //    startTargetActivityFromMain(context, ActivityPlayVideo.class);
    //}

    static void startVideoPlayActivity(Context context) {
        startTargetActivityFromMain(context, ActivityVideoList.class);
    }

    static void startImageListActivity(Context context) {
        startTargetActivityFromMain(context, ActivityMultiImage.class);
    }

    static void startThemeListActivity(Context context) {
        startTargetActivityFromMain(context, ActivityThemeList.class);
    }

    static void startAdapterTextView(Context context) {
        startTargetActivityFromMain(context, ActivityAdapterText.class);
    }

    static void startProgressTest(Context context) {
        startTargetActivityFromMain(context, ActivityProgressTest.class);
    }

    static void startBlogMainActivity(Context context) {
        startTargetActivityFromMain(context, MainBlogActivity.class);
    }

    static void startNormalCardActivity(Context context) {
        startTargetActivityFromMain(context, ActivityNormalCard.class);
    }

    static void startFloatTestActivity(Context context) {
        startTargetActivityFromMain(context, ActivityFloatTest.class);
    }

    static void startPagerTestActivity(Context context) {
        startTargetActivityFromMain(context, ActivityPagerTest.class);
    }

    static void startLocalImageActivity(Context context) {
        startTargetActivityFromMain(context, ActivityLocalImage.class);
    }

    static void startGameNumberActivity(Context context) {
        startTargetActivityFromMain(context, ActivityNumber.class);
    }

    static void startVolumeTestActivity(Context context) {
        startTargetActivityFromMain(context, ActivityVolume.class);
    }

    static void startNotifyTestActivity(Context context) {
        startTargetActivityFromMain(context, ActivityNotifyTest.class);
    }

    static void startEditImgTestActivity(Context context) {
        startTargetActivityFromMain(context, ActivityEditImage.class);
    }

    static void startShotScreenTestActivity(Context context) {
        startTargetActivityFromMain(context, ActivityShotScreen.class);
    }

    static void startSoftKeyboardActivity(Context context) {
        startTargetActivityFromMain(context, ActivitySoftKeyboard.class);
    }

    static void startCheckPermissionActivity(Context context) {
        startTargetActivityFromMain(context, ActivityPermissionTest.class);
    }

    static void startDialogFragmentTestActivity(Context context) {
        startTargetActivityFromMain(context, ActivitySlideTest.class);
    }

    static void startAudioPlayTest(Context context) {
        startTargetActivityFromMain(context, ActivityAudioList.class);
    }

    static void startExpandListView(Context context) {
        ActivityExpandableListView.open(context);
    }

    static void startDynamicSizeTest(Context context) {
        ActivityDynamicSize.open(context);
    }

    static void startBroadcastTest(Context context) {
        ActivityBroadcastTest.open(context);
    }

    static void startDatabaseTest(Context context) {
        ActivityDataBase.open(context);
    }

    static void dialogShowTest(Context context) {
        ActivityFullDialog.open(context);
    }

    static void frescoShowTest(Context context) {
        ActivityFrescoTest.open(context);
    }

    private static void startTargetActivityFromMain(Context context, Class targetActivity) {
        context.startActivity(new Intent(context, targetActivity));
    }
}
