package com.example.wyyu.gitsamlpe.test;

import android.os.Bundle;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
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

        listViewMain.addNewItem("GestureLockViewTest", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowManager.startGestureLockActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("FingerLockTest", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowManager.startFingerLockActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("FileListTest", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowManager.startFileListActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("FastRecyclerViewTest", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowManager.startFastRecyclerActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("LocationTest", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowManager.startLocationActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("AudioRecorder", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowManager.startAudioRecordActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("CardView", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowManager.startCardActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("PlayVideo", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startVideoPlayActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("ImageList", new View.OnClickListener() {
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

        listViewMain.addNewItem("CardTest", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startNormalCardActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("FloatTest", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startFloatTestActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("PagerTest", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startPagerTestActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("TextTest", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startAdapterTextView(MainActivity.this);
            }
        });

        listViewMain.addNewItem("ImageTest", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startLocalImageActivity(MainActivity.this);
                //MatisseHelper.openOnlySelectImage(MainActivity.this, 101);
                //UIShowManager.startPagerTestActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("GameNumber", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startGameNumberActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("VolumeTest", new View.OnClickListener() {
            @Override public void onClick(View view) {
                UIShowManager.startVolumeTestActivity(MainActivity.this);
            }
        });

        listViewMain.refreshList();
    }
}
