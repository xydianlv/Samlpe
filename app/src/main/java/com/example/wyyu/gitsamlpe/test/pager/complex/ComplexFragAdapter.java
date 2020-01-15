package com.example.wyyu.gitsamlpe.test.pager.complex;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by wyyu on 2020-01-15.
 **/

public class ComplexFragAdapter extends FragmentPagerAdapter {

    public ComplexFragAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override public Fragment getItem(int i) {
        return FragmentComplexTest.getFragment(i);
    }

    @Override public int getCount() {
        return 8;
    }
}
