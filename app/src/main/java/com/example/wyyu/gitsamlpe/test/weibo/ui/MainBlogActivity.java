package com.example.wyyu.gitsamlpe.test.weibo.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.touch.OnPressListenerAdapter;
import com.example.wyyu.gitsamlpe.framework.touch.TouchListenerLayout;

/**
 * Created by wyyu on 2018/4/17.
 **/

public class MainBlogActivity extends ToolbarActivity {

    //private TouchListenerLayout listenerLayout;
    private ViewPager viewPager;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_blog);

        initMainBlogActivity();
    }

    private void initMainBlogActivity() {

        initToolbar("首页", 0xffffffff, 0xff84919b);

        //listenerLayout = findViewById(R.id.listener_layout);
        viewPager = findViewById(R.id.main_view_pager);

        //listenerLayout.setOnPressListener(new OnPressListenerAdapter());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset,
                int positionOffsetPixels) {
                Log.d("Pager_Log_Position", "" + position);
                Log.d("Pager_Log_Offset", "" + positionOffset);
                Log.d("Pager_Log_Pixels", "" + positionOffsetPixels);
            }

            @Override public void onPageSelected(int position) {
                Log.d("Pager_Log_Func", "" + position);
            }

            @Override public void onPageScrollStateChanged(int state) {
                Log.d("Pager_Log_State", "" + state);
            }
        });
    }
}
