package com.example.wyyu.gitsamlpe.test.pager.slide.indicator;

/**
 * 可测量内容区域的指示器标题
 *
 * Created by wyyu on 2018/6/25.
 **/

public interface IMeasurablePagerTitleView extends IPagerTitleView {
    int getContentLeft();

    int getContentTop();

    int getContentRight();

    int getContentBottom();
}
