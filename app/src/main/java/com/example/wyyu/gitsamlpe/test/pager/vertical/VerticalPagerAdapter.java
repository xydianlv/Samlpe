package com.example.wyyu.gitsamlpe.test.pager.vertical;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.wyyu.gitsamlpe.test.pager.vertical.page.FragmentVerticalPagerB;
import com.example.wyyu.gitsamlpe.test.pager.vertical.recycler.FragmentVerticalPagerA;

/**
 * Created by wyyu on 2021/3/18.
 **/

public class VerticalPagerAdapter extends FragmentPagerAdapter {

    public VerticalPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull @Override public Fragment getItem(int position) {
        return FragmentVerticalPagerB.getFragment(position);
    }

    @Override public int getCount() {
        return 8;
    }
}
