package com.example.wyyu.gitsamlpe.test.slide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.BaseActivity;
import com.example.wyyu.gitsamlpe.test.slide.indicator.CommonNavigator;
import com.example.wyyu.gitsamlpe.test.slide.indicator.MagicIndicator;
import com.example.wyyu.gitsamlpe.test.slide.indicator.SimpleNavigatorAdapter;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2018/6/23.
 **/

public class ActivitySlideT extends BaseActivity {

    private static final String[] ITEMS = new String[] { "提醒", "私信" };

    private static final int FRAGMENT_COUNT = 2;

    @BindView(R.id.slide_indicator) MagicIndicator indicator;
    @BindView(R.id.slide_view_pager_t) ViewPager viewPager;

    private SimpleNavigatorAdapter navigatorAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_t);

        initViewPager();
        initIndicator();
    }

    private void initViewPager() {

        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset,
                int positionOffsetPixels) {
                if (indicator != null) {
                    indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override public void onPageSelected(int position) {
                if (indicator != null) {
                    indicator.onPageSelected(position);
                }
            }

            @Override public void onPageScrollStateChanged(int state) {
                if (indicator != null) {
                    indicator.onPageScrollStateChanged(state);
                }
            }
        });
    }

    private void initIndicator() {
        navigatorAdapter = new SimpleNavigatorAdapter(ITEMS, 16);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setSpace(UIUtils.dpToPx(9));
        commonNavigator.setIsNeedMargin(false);
        commonNavigator.setScrollPivotX(0.65f);
        commonNavigator.setAdapter(navigatorAdapter);
        indicator.setNavigator(commonNavigator);
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
