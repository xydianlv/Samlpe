package com.example.wyyu.gitsamlpe.test.pager.slide.weight;

import android.view.View;

/**
 * Created by wyyu on 2018/1/5.
 **/

public interface ISlideViewPager {

    void setOnLoadNextListener(OnLoadNextListener loadNextListener);

    void addNewView(View view);

    interface OnLoadNextListener {

        void loadNextView(int nextPosition);
    }
}
