package com.example.wyyu.gitsamlpe.test.pager.complex;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
