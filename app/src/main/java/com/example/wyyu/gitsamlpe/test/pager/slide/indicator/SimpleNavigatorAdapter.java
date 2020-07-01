package com.example.wyyu.gitsamlpe.test.pager.slide.indicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/6/25.
 **/

public class SimpleNavigatorAdapter extends CommonNavigatorAdapter {

    private ViewPager mViewPager;
    private int count;
    private String[] titles;
    private float textSize;

    private SimpleNavigatorAdapter(String[] titles) {
        if (titles == null || titles.length == 0) {
            this.count = 0;
            return;
        }
        this.count = titles.length;
        this.titles = titles;
        this.textSize = 0;
    }

    public SimpleNavigatorAdapter(String[] titles, float textSize) {
        this(titles);
        this.textSize = textSize;
    }

    public void registerViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
    }

    public void unregisterViewPager() {
        this.mViewPager = null;
    }

    @Override public int getCount() {
        return count;
    }

    @Override public IPagerTitleView getTitleView(Context context, final int index) {
        IndicatorTitleView titleView = new IndicatorTitleView(context);
        titleView.setText(titles[index]);
        titleView.setNormalColor(context.getResources().getColor(R.color.ct_2));
        titleView.setSelectedColor(context.getResources().getColor(R.color.ct_1));
        if (textSize != 0) {
            titleView.setTextSize(textSize);
        }

        if (mViewPager != null && mViewPager.getCurrentItem() == index) {
            titleView.onSelected(index, getCount());
        } else {
            titleView.onDeselected(index, getCount());
        }
        titleView.setOnClickListener(v -> {
            if (mViewPager != null) {
                mViewPager.setCurrentItem(index);
            }
        });
        return titleView;
    }

    @Override public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
        linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
        linePagerIndicator.setColors(context.getResources().getColor(R.color.color_84919b));
        float height = ViewCompatExt.dip2px(context.getResources(), 3.0f);
        linePagerIndicator.setLineWidth(ViewCompatExt.dip2px(context.getResources(), 9) * 1.0f);
        linePagerIndicator.setLineHeight(height);
        linePagerIndicator.setRoundRadius(height);
        return linePagerIndicator;
    }

    @Override public float getTitleWeight(int index) {
        return 1.0f;
    }
}
