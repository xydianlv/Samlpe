package com.example.wyyu.gitsamlpe.test.slide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.BaseActivity;

/**
 * Created by wyyu on 2018/6/23.
 **/

public class ActivitySlideT extends BaseActivity {

    private static final int FRAGMENT_COUNT = 10;

    @BindView(R.id.slide_view_pager_t) ViewPager viewPager;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_t);

        initViewPager();
    }

    private void initViewPager() {

        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);
    }

    private static class PagerAdapter extends FragmentPagerAdapter {

        PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override public Fragment getItem(int position) {
            return FragmentScroll.getFragment(position);
        }

        @Override public int getCount() {
            return FRAGMENT_COUNT;
        }
    }
}
