package com.example.wyyu.gitsamlpe.test;

import android.os.Bundle;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.anim.ActivityAnimTest;
import com.example.wyyu.gitsamlpe.test.audio.ActivityAudioTest;
import com.example.wyyu.gitsamlpe.test.bezier.ActivityBezierTest;
import com.example.wyyu.gitsamlpe.test.canvas.ActivityCanvasTest;
import com.example.wyyu.gitsamlpe.test.card.ActivityCardTest;
import com.example.wyyu.gitsamlpe.test.function.ActivityFunctionList;
import com.example.wyyu.gitsamlpe.test.network.ActivityNetworkTest;
import com.example.wyyu.gitsamlpe.test.text.ActivityTextTest;
import com.example.wyyu.gitsamlpe.util.file.FileManager;

public class MainActivity extends ToolbarActivity {

    private ListViewMain listViewMain;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMainActivity();
    }

    private void initMainActivity() {

        initToolBar();

        initListView();

        ULog.show(FileManager.getFileManager().getAudioDir());
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
            .addItem("Audio", v -> ActivityAudioTest.open(MainActivity.this))
            .addItem("Card", v -> ActivityCardTest.open(MainActivity.this))
            .addItem("Anim", v -> ActivityAnimTest.open(MainActivity.this))
            .addItem("Bezier", v -> ActivityBezierTest.open(MainActivity.this))
            .addItem("Text", v -> ActivityTextTest.open(MainActivity.this))
            .addItem("Canvas", v -> ActivityCanvasTest.open(MainActivity.this))
            .addItem("Net", v -> ActivityNetworkTest.open(MainActivity.this));

        listViewMain.addNewItem("FileListTest", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowManager.startFileListActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("FastRecyclerViewTest", new View.OnClickListener() {
            @Override public void onClick(View v) {
                //UIShowManager.startFastRecyclerActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("MultiImage", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startImageListActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("ThemeTest", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startThemeListActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("ProgressTest", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startProgressTest(MainActivity.this);
            }
        });

        listViewMain.addNewItem("SlideTest", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startSlideActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("FloatTest", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startFloatTestActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("BannerTest", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startPagerTestActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("TextTest", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startAdapterTextView(MainActivity.this);
            }
        });

        listViewMain.addNewItem("LocalImageList", new View.OnClickListener() {
            @Override public void onClick(View view) {
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
            @Override public void onClick(View view) {
                UIShowManager.startNotifyTestActivity(MainActivity.this);
            }
        });

        //listViewMain.addNewItem("EditImgTest", new View.OnClickListener() {
        //    @Override public void onClick(View view) {
        //        UIShowManager.startEditImgTestActivity(MainActivity.this);
        //    }
        //});

        listViewMain.addNewItem("SoftKeyboard", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startSoftKeyboardActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("PermissionTest", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startCheckPermissionActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("DialogFragment", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startDialogFragmentTestActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("BroadcastTest", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startBroadcastTest(MainActivity.this);
            }
        });

        listViewMain.addNewItem("DatabaseTest", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startDatabaseTest(MainActivity.this);
            }
        });

        listViewMain.addNewItem("DialogShowTest", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.dialogShowTest(MainActivity.this);
            }
        });

        listViewMain.addNewItem("FrescoShowTest", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.frescoShowTest(MainActivity.this);
            }
        });

        listViewMain.addNewItem("BlogTest", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startBlogMainActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("LiveTest", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startLiveTest(MainActivity.this);
            }
        });

        listViewMain.addNewItem("MultiHolderTest", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowManager.startMultiHolderTest(MainActivity.this);
            }
        });

        listViewMain.addNewItem("UiVisibilityTest", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowManager.startUiVisibilityTest(MainActivity.this);
            }
        });

        listViewMain.addNewItem("LocalVideoList", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowManager.startLocalVideoList(MainActivity.this);
            }
        });

        //listViewMain.addNewItem("BubblingTest", new View.OnClickListener() {
        //    @Override public void onClick(View v) {
        //        UIShowManager.startBubblingTest(MainActivity.this);
        //    }
        //});

        listViewMain.addNewItem("TangramTest", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowManager.startTangramTest(MainActivity.this);
            }
        });

        listViewMain.addNewItem("MultiListTest", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowManager.startMultiListTest(MainActivity.this);
            }
        });

        listViewMain.addNewItem("LithoTest", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowManager.startLithoTest(MainActivity.this);
            }
        });

        listViewMain.addNewItem("CalendarTest", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowManager.startCalendarTest(MainActivity.this);
            }
        });

        listViewMain.addNewItem("PagerTransformer", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowManager.startPagerTransformerShow(MainActivity.this);
            }
        });

        listViewMain.refreshList();
    }
}
