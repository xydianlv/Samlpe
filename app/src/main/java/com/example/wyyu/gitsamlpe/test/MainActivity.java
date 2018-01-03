package com.example.wyyu.gitsamlpe.test;

import android.os.Bundle;
import android.view.View;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

public class MainActivity extends ToolbarActivity {

    private ListViewMain listViewMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMainActivity();
    }

    private void initMainActivity() {

        initToolBar();

        initListView();
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
            @Override
            public void onClick(View v) {
                UIShowManager.startGestureLockActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("FingerLockTest", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIShowManager.startFingerLockActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("FastRecyclerViewTest", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIShowManager.startFastRecyclerActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("LocationTest", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIShowManager.startLocationActivity(MainActivity.this);
            }
        });

        listViewMain.addNewItem("SlideTest", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIShowManager.startSlideActivity(MainActivity.this);
            }
        });

        listViewMain.refreshList();
    }

}
