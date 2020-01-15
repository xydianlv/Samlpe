package com.example.wyyu.gitsamlpe.test.pager.complex;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by wyyu on 2020-01-15.
 **/

public class ComplexPageAdapter extends FragmentPagerAdapter {

    ComplexPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override public Fragment getItem(int i) {
        return FragmentComplexPage.getFragment(i);
    }

    @Override public int getCount() {
        return 3;
    }
}
