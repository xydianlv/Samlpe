package com.example.wyyu.gitsamlpe.test.theme;

import android.os.Bundle;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;

/**
 * Created by wyyu on 2018/4/12.
 **/

public class ActivityThemeList extends ToolbarActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_list);

        initActivityList();
    }

    private void initActivityList() {

        initToolBar();

        initListView();
    }

    private void initToolBar() {

        initToolbar("ActivityThemeList", 0xffffffff, 0xff84919b);
    }

    private void initListView() {

        ListViewMain listViewMain = findViewById(R.id.fun_list_view);

        listViewMain.addNewItem("DialogActivity", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowThemeManager.showDialogActivity(ActivityThemeList.this);
            }
        });

        listViewMain.addNewItem("WallPaperActivity", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowThemeManager.showWallPaperActivity(ActivityThemeList.this);
            }
        });

        listViewMain.addNewItem("TranslucentActivity", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowThemeManager.showTranslucentActivity(ActivityThemeList.this);
            }
        });

        listViewMain.addNewItem("PanelActivity", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowThemeManager.showPanelActivity(ActivityThemeList.this);
            }
        });

        listViewMain.addNewItem("NoDisplayActivity", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowThemeManager.showNoDisplayActivity(ActivityThemeList.this);
            }
        });

        listViewMain.addNewItem("FromBottomActivity", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowThemeManager.showBottomActivity(ActivityThemeList.this);
            }
        });

        listViewMain.addNewItem("StatusBarActivity", new View.OnClickListener() {
            @Override public void onClick(View v) {
                UIShowThemeManager.showStatusBarActivity(ActivityThemeList.this);
            }
        });

        listViewMain.refreshList();
    }
}
