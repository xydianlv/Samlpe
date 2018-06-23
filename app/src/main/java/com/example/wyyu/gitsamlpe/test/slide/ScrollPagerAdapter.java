package com.example.wyyu.gitsamlpe.test.slide;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2018/6/19.
 **/

public class ScrollPagerAdapter extends FragmentPagerAdapter {

    private static final int FRAGMENT_COUNT = 3;
    private List<FragmentScroll> fragmentScrollList;

    ScrollPagerAdapter(FragmentManager fm) {
        super(fm);

        fragmentScrollList = new ArrayList<>(FRAGMENT_COUNT);

        for (int index = 0; index < FRAGMENT_COUNT; index++) {
            fragmentScrollList.add(new FragmentScroll());
        }
    }

    @Override public FragmentScroll getItem(int position) {
        return fragmentScrollList == null ? null : fragmentScrollList.get(position);
    }

    @Override public int getCount() {
        return fragmentScrollList == null ? 0 : fragmentScrollList.size();
    }
}
