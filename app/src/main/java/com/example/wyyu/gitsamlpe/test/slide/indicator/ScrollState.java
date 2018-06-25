package com.example.wyyu.gitsamlpe.test.slide.indicator;

/**
 * 自定义滚动状态，消除对ViewPager的依赖
 *
 * Created by wyyu on 2018/6/25.
 **/

public interface ScrollState {
    int SCROLL_STATE_IDLE = 0;
    int SCROLL_STATE_DRAGGING = 1;
    int SCROLL_STATE_SETTLING = 2;
}
