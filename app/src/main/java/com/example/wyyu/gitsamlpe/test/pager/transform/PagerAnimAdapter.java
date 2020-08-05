package com.example.wyyu.gitsamlpe.test.pager.transform;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by wyyu on 2020-01-13.
 **/

public class PagerAnimAdapter extends FragmentPagerAdapter {

    private static final int FRAGMENT_COUNT = 3;

    PagerAnimAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override public Fragment getItem(int i) {
        return FragmentPagerTest.getFragment(i);
    }
}
