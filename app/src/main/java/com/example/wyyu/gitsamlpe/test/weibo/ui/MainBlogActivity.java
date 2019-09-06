package com.example.wyyu.gitsamlpe.test.weibo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.test.weibo.weight.BottomSwitchTab;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2018/4/17.
 **/

public class MainBlogActivity extends FullScreenActivity {

    @BindView(R.id.blog_switch_tab) BottomSwitchTab switchTab;
    @BindView(R.id.blog_view_pager) ViewPager viewPager;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_blog);

        initActivity();
    }

    private void initActivity() {
        initViewPager();
        initSwitchTab();
    }

    private void initViewPager() {

        viewPager.setAdapter(new BlogPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int i, float v, int i1) {

            }

            @Override public void onPageSelected(int i) {
                if (switchTab != null) {
                    switchTab.refreshSelectStatus(i);
                }
            }

            @Override public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initSwitchTab() {
        switchTab.setOnSwitchTabClickListener(new BottomSwitchTab.OnSwitchTabClickListener() {
            @Override public void onSwitch(int index) {
                if (viewPager != null) {
                    viewPager.setCurrentItem(index);
                }
            }
        });
    }

    private class BlogPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        BlogPagerAdapter(FragmentManager fm) {
            super(fm);

            fragmentList = new ArrayList<>(4);

            fragmentList.add(FragmentNormal.getFragment(0));
            fragmentList.add(FragmentNormal.getFragment(1));
            fragmentList.add(FragmentSwitch.getFragment());
            fragmentList.add(FragmentMe.getFragment());
        }

        @Override public Fragment getItem(int i) {
            return fragmentList == null ? null : fragmentList.get(i);
        }

        @Override public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }
    }
}
