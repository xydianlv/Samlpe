package com.example.wyyu.gitsamlpe.framework.activity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.toolbar.ToolbarMenuMgr;

/**
 * Created by wyyu on 2017/12/28.
 * 继承自 ToolbarActivity 的界面布局中，必须包含 <include layout="@layout/common_toolbar" /> 供父类初始化 Toolbar
 **/

public class ToolbarActivity extends BaseActivity {

    private static final int THEME = R.style.ToolbarActivityTheme;

    private ToolbarMenuMgr toolbarMenuMgr;
    private Toolbar toolbar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        setTheme(THEME);
        super.onCreate(savedInstanceState);
    }

    public void addToolbarMenu(int menuIcon, String menuTitle,
        Toolbar.OnMenuItemClickListener clickListener) {

        if (toolbar == null) return;

        if (toolbarMenuMgr == null) toolbarMenuMgr = new ToolbarMenuMgr(this, toolbar);

        toolbarMenuMgr.addMenu(menuIcon, menuTitle, clickListener);
    }

    public void addToolbarMenu(String menuTitle, Toolbar.OnMenuItemClickListener clickListener) {

        addToolbarMenu(0, menuTitle, clickListener);
    }

    public void clearToolbarMenu() {
        if (toolbarMenuMgr == null) return;
        toolbarMenuMgr.clearMenu();
    }

    public void initToolbar(String title) {
        initToolbar(title, 0xffffffff, 0xff84919b);
    }

    public void initToolbar(String title, int titleColor) {

        if (toolbar == null) toolbar = findViewById(R.id.toolbar);

        toolbar.setTitleTextColor(titleColor);

        setTitle(title);

        setSupportActionBar(toolbar);
    }

    public void initToolbar(String title, int titleColor, int backColor) {

        initToolbar(title, titleColor);

        toolbar.setBackgroundColor(backColor);
    }

    public void initToolbar(String title, int titleColor, int navigationIcon,
        View.OnClickListener clickListener) {

        initToolbar(title, titleColor);

        toolbar.setNavigationIcon(navigationIcon);

        toolbar.setNavigationOnClickListener(clickListener);
    }

    public void initToolbar(String title, int titleColor, int backColor, int navigationIcon,
        View.OnClickListener clickListener) {

        initToolbar(title, titleColor, navigationIcon, clickListener);

        toolbar.setBackgroundColor(backColor);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        return (toolbarMenuMgr != null && toolbarMenuMgr.onCreateOptionsMenu(menu))
            || super.onCreateOptionsMenu(menu);
    }
}
