package com.example.wyyu.gitsamlpe.test;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.anim.ActivityAnimTest;
import com.example.wyyu.gitsamlpe.test.audio.ActivityAudioTest;
import com.example.wyyu.gitsamlpe.test.bezier.ActivityBezierTest;
import com.example.wyyu.gitsamlpe.test.canvas.ActivityCanvasTest;
import com.example.wyyu.gitsamlpe.test.card.ActivityCardTest;
import com.example.wyyu.gitsamlpe.test.file.ActivityFileListTest;
import com.example.wyyu.gitsamlpe.test.fresco.ActivityFrescoTest;
import com.example.wyyu.gitsamlpe.test.function.ActivityFunctionList;
import com.example.wyyu.gitsamlpe.test.function.shortcut.ActivityShotCut;
import com.example.wyyu.gitsamlpe.test.function.shortcut.Constants;
import com.example.wyyu.gitsamlpe.test.gl.ActivityGlTest;
import com.example.wyyu.gitsamlpe.test.keyboard.ActivitySoftKeyboardTest;
import com.example.wyyu.gitsamlpe.test.network.ActivityNetworkTest;
import com.example.wyyu.gitsamlpe.test.pager.ActivityPagerTest;
import com.example.wyyu.gitsamlpe.test.text.ActivityTextTest;
import com.example.wyyu.gitsamlpe.test.video.ActivityVideoTest;
import com.example.wyyu.gitsamlpe.util.file.FileManager;

public class MainActivity extends ToolbarActivity {

    private ListViewMain listViewMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMainActivity();
    }

    private void initMainActivity() {
        dispatchIntent();
        initToolBar();
        initListView();
        ULog.show(FileManager.getFileManager().getAudioDir());
    }

    private void dispatchIntent() {
        Intent intent = getIntent();
        String fromString = intent.getStringExtra(Constants.KEY_FROM_STRING);
        if (TextUtils.isEmpty(fromString)) {
            return;
        }
        if (Constants.KEY_FROM_SHOT_CUT.equals(fromString)) {
            ActivityShotCut.open(this);
        }
    }

    private void initToolBar() {
        initToolbar("Test", 0xffffffff, 0xff84919b);
    }

    private void initListView() {

        listViewMain = findViewById(R.id.list_view_main);

        refreshListView();
    }

    private void refreshListView() {

        listViewMain.addItem("Function", v -> ActivityFunctionList.open(MainActivity.this))
                .addItem("OpenGL", v -> ActivityGlTest.open(MainActivity.this))
                .addItem("Algorithm", v -> ActivityFunctionList.open(MainActivity.this))
                .addItem("Audio", v -> ActivityAudioTest.open(MainActivity.this))
                .addItem("Card", v -> ActivityCardTest.open(MainActivity.this))
                .addItem("Anim", v -> ActivityAnimTest.open(MainActivity.this))
                .addItem("Bezier", v -> ActivityBezierTest.open(MainActivity.this))
                .addItem("Text", v -> ActivityTextTest.open(MainActivity.this))
                .addItem("Canvas", v -> ActivityCanvasTest.open(MainActivity.this))
                .addItem("Net", v -> ActivityNetworkTest.open(MainActivity.this))
                .addItem("Video", v -> ActivityVideoTest.open(MainActivity.this))
                .addItem("Pager", v -> ActivityPagerTest.open(MainActivity.this))
                .addItem("Fresco", v -> ActivityFrescoTest.open(MainActivity.this))
                .addItem("Keyboard", v -> ActivitySoftKeyboardTest.open(MainActivity.this))
                .addItem("FileList", v -> ActivityFileListTest.open(MainActivity.this));

        listViewMain.addNewItem("FastRecyclerViewTest", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UIShowManager.startFastRecyclerActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("MultiImage", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIShowManager.startImageListActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("ThemeTest", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIShowManager.startThemeListActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("ProgressTest", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIShowManager.startProgressTest(MainActivity.this);
            }
        });

        listViewMain.addNewItem("FloatTest", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIShowManager.startFloatTestActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("LocalImageList", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIShowManager.startLocalImageActivity(MainActivity.this);
                //MatisseHelper.openOnlySelectImage(MainActivity.this, 101);
                //UIShowManager.startPagerTestActivity(MainActivity.this);
            }
        });

        //listViewMain.addNewItem("GameNumber", new View.OnClickListener() {
        //    @Override public void onClick(View view) {
        //        UIShowManager.startGameNumberActivity(MainActivity.this);
        //    }
        //});

        listViewMain.addNewItem("NotifyTest", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIShowManager.startNotifyTestActivity(MainActivity.this);
            }
        });

        //listViewMain.addNewItem("EditImgTest", new View.OnClickListener() {
        //    @Override public void onClick(View view) {
        //        UIShowManager.startEditImgTestActivity(MainActivity.this);
        //    }
        //});

        listViewMain.addNewItem("SoftKeyboard", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIShowManager.startSoftKeyboardActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("PermissionTest", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIShowManager.startCheckPermissionActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("DialogFragment", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIShowManager.startDialogFragmentTestActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("BroadcastTest", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIShowManager.startBroadcastTest(MainActivity.this);
            }
        });

        listViewMain.addNewItem("DatabaseTest", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIShowManager.startDatabaseTest(MainActivity.this);
            }
        });

        listViewMain.addNewItem("DialogShowTest", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIShowManager.dialogShowTest(MainActivity.this);
            }
        });

        listViewMain.addNewItem("LiveTest", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIShowManager.startLiveTest(MainActivity.this);
            }
        });

        listViewMain.addNewItem("MultiHolderTest", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIShowManager.startMultiHolderTest(MainActivity.this);
            }
        });

        listViewMain.addNewItem("UiVisibilityTest", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIShowManager.startUiVisibilityTest(MainActivity.this);
            }
        });

        //listViewMain.addNewItem("BubblingTest", new View.OnClickListener() {
        //    @Override public void onClick(View v) {
        //        UIShowManager.startBubblingTest(MainActivity.this);
        //    }
        //});

        listViewMain.addNewItem("TangramTest", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIShowManager.startTangramTest(MainActivity.this);
            }
        });

        listViewMain.addNewItem("MultiListTest", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIShowManager.startMultiListTest(MainActivity.this);
            }
        });

        //listViewMain.addNewItem("LithoTest", new View.OnClickListener() {
        //    @Override public void onClick(View v) {
        //        UIShowManager.startLithoTest(MainActivity.this);
        //    }
        //});

        listViewMain.addNewItem("CalendarTest", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIShowManager.startCalendarTest(MainActivity.this);
            }
        });

        listViewMain.refreshList();
    }
}
