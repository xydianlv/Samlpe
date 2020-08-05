package com.example.wyyu.gitsamlpe.test.pager.ultra;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.viewpager.UltraViewPager;
import com.example.wyyu.gitsamlpe.framework.viewpager.indicator.IndicatorView;
import com.example.wyyu.gitsamlpe.framework.viewpager.transformer.UltraScaleTransformer;
import com.example.wyyu.gitsamlpe.test.pager.UltraPagerAdapter;

/**
 * Created by wyyu on 2020-06-24.
 **/

public class ActivityUltraTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityUltraTest.class));
    }

    @BindView(R.id.ultra_test_indicator) IndicatorView indicatorView;
    @BindView(R.id.ultra_test_pager) UltraViewPager viewPager;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultra_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("UltraTest");

        indicatorView.setIndicator(3, 0xffffffff, 0xffef5464);

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
}
