package com.example.wyyu.gitsamlpe.test.pager;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.viewpager.UltraViewPager;
import com.example.wyyu.gitsamlpe.framework.viewpager.indicator.IndicatorView;
import com.example.wyyu.gitsamlpe.framework.viewpager.transformer.UltraScaleTransformer;

/**
 * Created by wyyu on 2018/6/29.
 **/

public class ActivityPagerTest extends ToolbarActivity {

    @BindView(R.id.ultra_view_indicator) IndicatorView indicatorView;
    @BindView(R.id.ultra_view_pager) UltraViewPager viewPager;

    private UltraViewPager.Orientation gravityIndicator;
    private PagerAdapter pagerAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("PagerTest", 0xffffffff, 0xff84919b);
        initViewPager();
        initIndicator();
    }

    private void initViewPager() {
        viewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        pagerAdapter = new UltraPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        viewPager.setMultiScreen(0.86f);
        viewPager.setMaxHeight(800);
        viewPager.setInfiniteLoop(true);
        viewPager.setAutoMeasureHeight(true);
        gravityIndicator = UltraViewPager.Orientation.HORIZONTAL;
        viewPager.setPageTransformer(false, new UltraScaleTransformer());

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset,
                int positionOffsetPixels) {
            }

            @Override public void onPageSelected(int position) {
                indicatorView.refreshIndicator(position % 3);
            }

            @Override public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initIndicator() {
        indicatorView.setIndicator(3, 0xffffffff, 0xffef5464);
    }
}
