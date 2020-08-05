package com.example.wyyu.gitsamlpe.framework.toolbar;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.wyyu.gitsamlpe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class ToolbarMenuMgr {

    private Toolbar.OnMenuItemClickListener menuItemClickListener;

    private List<MenuItemData> menuItemDataList;

    private Context context;
    private Toolbar toolbar;

    private boolean showMenu;

    public ToolbarMenuMgr(Context context, Toolbar toolbar) {
        this.context = context;
        this.toolbar = toolbar;

        initBasicData();
    }

    private void initBasicData() {
        menuItemDataList = new ArrayList<>();
        showMenu = true;

        menuItemClickListener = new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                for (MenuItemData itemData : menuItemDataList) {
                    if (item.getTitle().equals(itemData.getMenuItemTitle())) {
                        itemData.getClickListener().onMenuItemClick(item);
                        return true;
                    }
                }
                return false;
            }
        };
    }


    public void addMenu(int menuIcon, String menuTitle, Toolbar.OnMenuItemClickListener clickListener) {
        for (MenuItemData itemData : menuItemDataList) {
            if (itemData.getMenuItemTitle().equals(menuTitle))
                return;
        }

        menuItemDataList.add(new MenuItemData(clickListener, menuTitle, menuIcon));
        toolbar.setOnMenuItemClickListener(menuItemClickListener);
    }

    public void showMenu() {
        if (context != null) {
            this.showMenu = true;
            ((AppCompatActivity) context).invalidateOptionsMenu();
        }
    }

    public void hideMenu() {
        if (context != null) {
            this.showMenu = false;
            ((AppCompatActivity) context).invalidateOptionsMenu();
        }
    }

    public void clearMenu() {
        menuItemDataList.clear();
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        if (context == null || !showMenu) return false;

        ((AppCompatActivity) context).getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        List<MenuItem> menuItemList = new ArrayList<>();

        menuItemList.add(menu.findItem(R.id.menu_one));
        menuItemList.add(menu.findItem(R.id.menu_two));
        menuItemList.add(menu.findItem(R.id.menu_three));

        if (menuItemList.size() < menuItemDataList.size()) {
            setMaxMenuLayout(menuItemList, menu);
        } else {
            setMinMenuLayout(menuItemList);
        }

        return true;
    }

    // 当添加的按钮数小于初始设定的按钮个数时，调用该方法
    private void setMinMenuLayout(List<MenuItem> menuItemList) {
        for (int index = 0; index < menuItemList.size(); index++) {
            MenuItem menuItem = menuItemList.get(index);
            if (index < menuItemDataList.size()) {
                menuItem.setTitle(menuItemDataList.get(index).getMenuItemTitle());
                menuItem.setIcon(menuItemDataList.get(index).getMenuItemIcon());
            } else {
                menuItem.setVisible(false);
            }
        }
    }

    // 当添加的按钮数大于初始设定的按钮个数，调用该方法
    private void setMaxMenuLayout(List<MenuItem> menuItemList, Menu menu) {
        for (int index = 0; index < menuItemDataList.size(); index++) {
            if (index < menuItemList.size()) {
                menuItemList.get(index).setTitle(menuItemDataList.get(index).getMenuItemTitle());
                menuItemList.get(index).setIcon(menuItemDataList.get(index).getMenuItemIcon());
            } else {
                menu.add(menuItemDataList.get(index).getMenuItemTitle());
            }
        }
    }

}
