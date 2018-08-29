package com.example.wyyu.gitsamlpe.test.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2018/8/28.
 **/

public class ActivitySlideTest extends ToolbarActivity {

    @BindView(R.id.slide_test_viewpager) ViewPager viewPager;

    private PagerAdapter pagerAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_test);

        initViewPager();
    }

    private void initViewPager() {

        initToolbar("SlidePager", 0xffffffff, 0xff84919b);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(0);
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && FragmentBottom.isShowing) {
            pagerAdapter.onKeyBackClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
