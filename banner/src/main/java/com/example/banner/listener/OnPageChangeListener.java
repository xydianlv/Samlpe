package com.example.banner.listener;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wyyu on 2019-07-01.
 **/

public interface OnPageChangeListener {

    void onScrollStateChanged(RecyclerView recyclerView, int newState);

    void onScrolled(RecyclerView recyclerView, int dx, int dy);

    void onPageSelected(int index);
}
