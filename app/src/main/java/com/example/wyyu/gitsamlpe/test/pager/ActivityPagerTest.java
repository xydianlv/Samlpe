package com.example.wyyu.gitsamlpe.test.pager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.BindView;
import com.example.banner.ConvenientBanner;
import com.example.banner.holder.CBViewHolderCreator;
import com.example.banner.holder.Holder;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.viewpager.UltraViewPager;
import com.example.wyyu.gitsamlpe.framework.viewpager.indicator.IndicatorView;
import com.example.wyyu.gitsamlpe.framework.viewpager.transformer.UltraScaleTransformer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2018/6/29.
 **/

public class ActivityPagerTest extends ToolbarActivity {

    @BindView(R.id.ultra_view_indicator) IndicatorView indicatorView;
    @BindView(R.id.ultra_view_pager) UltraViewPager viewPager;
    @BindView(R.id.convenient_banner) ConvenientBanner<Integer> convenientBanner;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("PagerTest", 0xffffffff, 0xff84919b);
        initViewPager();
        initIndicator();
        initBanner();
    }

    private void initViewPager() {
        viewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        UltraPagerAdapter pagerAdapter = new UltraPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        viewPager.setMultiScreen(0.86f);
        viewPager.setMaxHeight(800);
        viewPager.setInfiniteLoop(true);
        viewPager.setAutoMeasureHeight(true);
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

    private void initBanner() {
        List<Integer> imageList = new ArrayList<>(3);
        for (int index = 0; index < 3; index++) {
            imageList.add(R.mipmap.image_test_2);
        }
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override public Holder createHolder(View itemView) {
                return new BannerHolder(itemView);
            }

            @Override public int getLayoutId() {
                return R.layout.layout_banner_holder;
            }
        }, imageList)
            .setCanLoop(true)
            .setPageIndicator(
                new int[] { R.drawable.button_circle_back_grey, R.drawable.button_circle_back_red })
            .setCurrentItem(0, true)
            .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }
}
