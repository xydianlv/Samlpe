package com.example.wyyu.gitsamlpe.test.slide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.BaseActivity;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
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

    private int presentPosition;
    private boolean hasChange;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_t);

        initViewPager();
        initIndicator();
    }

    private void initViewPager() {

        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset,
                int positionOffsetPixels) {
                if (indicator != null) {
                    indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
                presentPosition = position;
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
                if (state == 1) {
                    hasChange = false;
                } else if (state == 2) {
                    hasChange = true;
                }
                if (state == 0 && !hasChange && presentPosition == pagerAdapter.getCount() - 1) {
                    UToast.showShort(ActivitySlideT.this, "最后一页");
                }
            }
        });
    }

    private void initIndicator() {
        navigatorAdapter = new SimpleNavigatorAdapter(ITEMS, 16);

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(navigatorAdapter);
        commonNavigator.setSpace(UIUtils.dpToPx(9));
        commonNavigator.setIsNeedMargin(true);
        commonNavigator.setAdjustMode(true);

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
