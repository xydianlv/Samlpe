package com.example.wyyu.gitsamlpe.test.function;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;
import com.example.wyyu.gitsamlpe.test.function.download.ActivityDownloadTest;
import com.example.wyyu.gitsamlpe.test.function.dynamic.ActivityDynamicSize;
import com.example.wyyu.gitsamlpe.test.function.grid.ActivityGridTest;
import com.example.wyyu.gitsamlpe.test.function.shot.ActivityShotScreen;
import com.example.wyyu.gitsamlpe.test.function.expandable.ActivityExpandableListView;
import com.example.wyyu.gitsamlpe.test.function.location.ActivityLocation;
import com.example.wyyu.gitsamlpe.test.function.finger.ActivityFingerLockTest;
import com.example.wyyu.gitsamlpe.test.function.gesture.ActivityGestureLockTest;
import com.example.wyyu.gitsamlpe.test.function.nine.ActivityNinePatchTest;
import com.example.wyyu.gitsamlpe.test.function.percent.ActivityPercentTest;
import com.example.wyyu.gitsamlpe.test.function.detector.ActivityGestureDetector;
import com.example.wyyu.gitsamlpe.test.function.wallpaper.ActivityWallpaperTest;

/**
 * Created by wyyu on 2020-04-16.
 **/

public class ActivityFunctionList extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityFunctionList.class));
    }

    @BindView(R.id.function_list) ListViewMain listView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_list);

        initActivity();
    }

    private void initActivity() {
        initToolbar("FunctionList", 0xffffffff, 0xff84919b);

        listView.addItem("GestureLock",
            v -> ActivityGestureLockTest.open(ActivityFunctionList.this))
            .addItem("FingerLock", v -> ActivityFingerLockTest.open(ActivityFunctionList.this))
            .addItem("Location", v -> ActivityLocation.open(ActivityFunctionList.this))
            .addItem("ShotScreen", v -> ActivityShotScreen.open(ActivityFunctionList.this))
            .addItem("ExpandList", v -> ActivityExpandableListView.open(ActivityFunctionList.this))
            .addItem("DynamicSize", v -> ActivityDynamicSize.open(ActivityFunctionList.this))
            .addItem("PercentView", v -> ActivityPercentTest.open(ActivityFunctionList.this))
            .addItem("DetectorTest", v -> ActivityGestureDetector.open(ActivityFunctionList.this))
            .addItem("NinePatch", v -> ActivityNinePatchTest.open(ActivityFunctionList.this))
            .addItem("DownloadTest", v -> ActivityDownloadTest.open(ActivityFunctionList.this))
            .addItem("WallpaperTest", v -> ActivityWallpaperTest.open(ActivityFunctionList.this))
            .addItem("GridTest", v -> ActivityGridTest.open(ActivityFunctionList.this))
            .refreshList();
    }
}
