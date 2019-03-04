package com.example.wyyu.gitsamlpe.test.visibility;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;

/**
 * Created by wyyu on 2019/3/4.
 **/

public class ActivitySystemUiVisibility extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivitySystemUiVisibility.class));
    }

    public static int SYSTEM_UI_VISIBILITY = 0;
    public static int BUILD_VERSION = Build.VERSION_CODES.BASE;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_list);

        initActivity();
    }

    private void initActivity() {
        initToolbar("SystemUiVisibility");
        initListView();
    }

    private void initListView() {

        ListViewMain listViewMain = findViewById(R.id.fun_list_view);

        listViewMain.addNewItem("SYSTEM_UI_FLAG_FULLSCREEN", new View.OnClickListener() {
            @Override public void onClick(View v) {
                setAndOpen(View.SYSTEM_UI_FLAG_FULLSCREEN, Build.VERSION_CODES.BASE);
            }
        });
        listViewMain.addNewItem("SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN", new View.OnClickListener() {
            @Override public void onClick(View v) {
                setAndOpen(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN, Build.VERSION_CODES.BASE);
            }
        });
        listViewMain.addNewItem("SYSTEM_UI_FLAG_VISIBLE", new View.OnClickListener() {
            @Override public void onClick(View v) {
                setAndOpen(View.SYSTEM_UI_FLAG_VISIBLE, Build.VERSION_CODES.BASE);
            }
        });
        listViewMain.addNewItem("SYSTEM_UI_FLAG_LOW_PROFILE", new View.OnClickListener() {
            @Override public void onClick(View v) {
                setAndOpen(View.SYSTEM_UI_FLAG_LOW_PROFILE, Build.VERSION_CODES.BASE);
            }
        });
        listViewMain.addNewItem("SYSTEM_UI_FLAG_LAYOUT_STABLE", new View.OnClickListener() {
            @Override public void onClick(View v) {
                setAndOpen(View.SYSTEM_UI_FLAG_LAYOUT_STABLE, Build.VERSION_CODES.BASE);
            }
        });
        listViewMain.addNewItem("SYSTEM_UI_FLAG_HIDE_NAVIGATION", new View.OnClickListener() {
            @Override public void onClick(View v) {
                setAndOpen(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION, Build.VERSION_CODES.BASE);
            }
        });
        listViewMain.addNewItem("SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION", new View.OnClickListener() {
            @Override public void onClick(View v) {
                setAndOpen(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION, Build.VERSION_CODES.BASE);
            }
        });
        listViewMain.addNewItem("SYSTEM_UI_FLAG_IMMERSIVE", new View.OnClickListener() {
            @Override public void onClick(View v) {
                setAndOpen(View.SYSTEM_UI_FLAG_IMMERSIVE, Build.VERSION_CODES.KITKAT);
            }
        });
        listViewMain.addNewItem("SYSTEM_UI_FLAG_IMMERSIVE_STICKY", new View.OnClickListener() {
            @Override public void onClick(View v) {
                setAndOpen(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY, Build.VERSION_CODES.KITKAT);
            }
        });
        listViewMain.addNewItem("SYSTEM_UI_FLAG_LIGHT_STATUS_BAR", new View.OnClickListener() {
            @Override public void onClick(View v) {
                setAndOpen(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR, Build.VERSION_CODES.M);
            }
        });
        listViewMain.addNewItem("SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR", new View.OnClickListener() {
            @Override public void onClick(View v) {
                setAndOpen(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR, Build.VERSION_CODES.O);
            }
        });
        listViewMain.refreshList();
    }

    private void setAndOpen(int paramsType, int versionCode) {
        SYSTEM_UI_VISIBILITY = paramsType;
        BUILD_VERSION = versionCode;
        ActivityUiVisibilityTest.open(ActivitySystemUiVisibility.this);
    }
}
