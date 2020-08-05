package com.example.wyyu.gitsamlpe.framework.toolbar;

import androidx.appcompat.widget.Toolbar;

/**
 * Created by wyyu on 2017/12/28.
 **/

class MenuItemData {

    private Toolbar.OnMenuItemClickListener clickListener;
    private String menuItemTitle;
    private int menuItemIcon;

    MenuItemData(Toolbar.OnMenuItemClickListener clickListener, String menuItemTitle, int menuItemIcon) {
        this.clickListener = clickListener;
        this.menuItemTitle = menuItemTitle;
        this.menuItemIcon = menuItemIcon;
    }

    Toolbar.OnMenuItemClickListener getClickListener() {
        return clickListener;
    }

    String getMenuItemTitle() {
        return menuItemTitle;
    }

    int getMenuItemIcon() {
        return menuItemIcon;
    }
}
