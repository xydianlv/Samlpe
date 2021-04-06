package com.example.wyyu.gitsamlpe.test.pager.vertical.page;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by wyyu on 2021/3/18.
 **/

public class ChildPagerAdapter extends FragmentPagerAdapter {

    public ChildPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull @Override public Fragment getItem(int position) {
        return FragmentPagerChild.getFragment(position);
    }

    @Override public int getCount() {
        return 6;
    }
}
