package com.example.wyyu.gitsamlpe.test.slide.indicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import com.example.wyyu.gitsamlpe.R;
import java.util.HashMap;

/**
 * Created by wyyu on 2018/6/25.
 **/

public class SimpleNavigatorAdapter extends CommonNavigatorAdapter {

    private ViewPager mViewPager;
    private int count;
    private String[] titles;
    private HashMap<String, Integer> crumbData = new HashMap<>();
    private float textSize;

    public SimpleNavigatorAdapter(String[] titles) {
        if (titles == null || titles.length == 0) {
            this.count = 0;
            return;
        }
        this.count = titles.length;
        this.titles = titles;
        this.textSize = 0;
    }

    public SimpleNavigatorAdapter(String[] titles,float textSize) {
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
        titleView.setNormalColor(context.getResources().getColor(R.color.color_9fbfbf));
        titleView.setSelectedColor(context.getResources().getColor(R.color.color_orange));
        if (textSize != 0) {
            titleView.setTextSize(textSize);
        }

        if (mViewPager != null && mViewPager.getCurrentItem() == index) {
            titleView.onSelected(index, getCount());
        } else {
            titleView.onDeselected(index, getCount());
        }
        titleView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (mViewPager != null) {
                    mViewPager.setCurrentItem(index);
                }
            }
        });
        return titleView;
    }

    @Override public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
        linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
        linePagerIndicator.setColors(context.getResources().getColor(R.color.color_green));
        float height = ViewCompatExt.dip2px(context.getResources(), 3.0f);
        linePagerIndicator.setLineWidth(ViewCompatExt.dip2px(context.getResources(), 9) * 1.0f);
        linePagerIndicator.setLineHeight(height);
        linePagerIndicator.setRoundRadius(height);
        //linePagerIndicator.setStartInterpolator(new AccelerateInterpolator());
        //linePagerIndicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
        return linePagerIndicator;
    }

    @Override public float getTitleWeight(int index) {
        return 1.0f;
    }

    /**
     * @param title 是显示的title
     * @param crumbCount -1时不显示
     */
    public void setCrumbCount(String title, int crumbCount) {
        crumbData.put(title, crumbCount);
        notifyDataSetChanged();
    }

    public void changeTitleTxt(int index, String newTitle) {
        if (index < titles.length) {
            titles[index] = newTitle;
            notifyDataSetChanged();
        }
    }
}
