package com.example.wyyu.gitsamlpe.test.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2018/8/28.
 **/

public class PagerAdapter extends FragmentPagerAdapter {

    private List<FragmentSlideTest> fragmentList;

    public PagerAdapter(FragmentManager fm) {
        super(fm);

        fragmentList = new ArrayList<>(2);

        fragmentList.add(FragmentSlideTest.getFragment(0));
        fragmentList.add(FragmentSlideTest.getFragment(1));
    }

    @Override public Fragment getItem(int position) {
        return fragmentList == null ? null : fragmentList.get(position);
    }

    @Override public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

    void onKeyBackClick() {
        if (fragmentList == null || fragmentList.isEmpty()) {
            return;
        }
        for (FragmentSlideTest fragmentSlideTest : fragmentList) {
            fragmentSlideTest.onKeyBackClick();
        }
    }
}
