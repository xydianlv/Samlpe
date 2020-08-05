package com.example.wyyu.gitsamlpe.test.pager.complex;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
