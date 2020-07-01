package com.example.wyyu.gitsamlpe.test.pager.slide.data;

/**
 * Created by wyyu on 2018/1/4.
 **/

public class ViewData {

    public String getViewText() {
        return viewText;
    }

    public void setViewText(String viewText) {
        this.viewText = viewText;
    }

    public int getViewColor() {
        return viewColor;
    }

    public void setViewColor(int viewColor) {
        this.viewColor = viewColor;
    }

    private String viewText;
    private int viewColor;

    public ViewData(String viewText, int viewColor) {
        this.viewColor = viewColor;
        this.viewText = viewText;
    }

    public ViewData() {

    }
}
