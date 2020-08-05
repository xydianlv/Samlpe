package com.example.wyyu.gitsamlpe.test.pager.slide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import com.example.wyyu.gitsamlpe.test.pager.slide.indicator.CommonNavigator;
import com.example.wyyu.gitsamlpe.test.pager.slide.indicator.MagicIndicator;
import com.example.wyyu.gitsamlpe.test.pager.slide.indicator.SimpleNavigatorAdapter;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2018/6/23.
 **/

public class ActivitySlideTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivitySlideTest.class));
    }

    @BindView(R.id.slide_indicator) MagicIndicator indicator;
    @BindView(R.id.slide_view_pager_t) ViewPager viewPager;

    private static final String[] ITEMS = new String[] { "提醒", "私信" };

    private SimpleNavigatorAdapter navigatorAdapter;

    private boolean hasChange;
    private int onPosition;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_t);

        initToolbar("SlideTest");
        initViewPager();
        initIndicator();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (navigatorAdapter != null) {
            navigatorAdapter.unregisterViewPager();
        }
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
                onPosition = position;
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
                if (state == 0 && !hasChange && onPosition == pagerAdapter.getCount() - 1) {
                    UToast.showShort(ActivitySlideTest.this, "最后一页");
                }
            }
        });
    }

    private void initIndicator() {
        navigatorAdapter = new SimpleNavigatorAdapter(ITEMS, 16);
        navigatorAdapter.registerViewPager(viewPager);

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
            return ITEMS.length;
        }
    }
}
