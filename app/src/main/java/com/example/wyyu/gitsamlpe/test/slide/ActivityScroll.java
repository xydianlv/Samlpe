package com.example.wyyu.gitsamlpe.test.slide;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.BaseActivity;

/**
 * Created by wyyu on 2018/6/19.
 **/

public class ActivityScroll extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.scroll_view_pager) ViewPager viewPager;

    private ScrollPagerAdapter scrollPagerAdapter;

    private int presentPosition;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

        initActivity();
    }

    private void initActivity() {
        scrollPagerAdapter = new ScrollPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(scrollPagerAdapter);
        viewPager.addOnPageChangeListener(this);

        viewPager.setCurrentItem(2);
        presentPosition = 0;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d("Pager_Log_Position", "" + position);
        Log.d("Pager_Log_Offset", "" + positionOffset);
        Log.d("Pager_Log_Pixels", "" + positionOffsetPixels);
    }

    @Override public void onPageSelected(int position) {
        Log.d("Pager_Log_Func", "" + position);

        //if (viewPager.getCurrentItem() != 1 && presentPosition >= 0) {
        //    viewPager.setCurrentItem(1, false);
        //}
        //if (position > 1) {
        //    presentPosition++;
        //}
        //if (position < 1) {
        //    presentPosition--;
        //}
        //if (scrollPagerAdapter != null) {
        //    scrollPagerAdapter.getItem(position).onSelect(presentPosition);
        //}
    }

    @Override public void onPageScrollStateChanged(int state) {
        Log.d("Pager_Log_State", "" + state);
    }
}
