package com.example.wyyu.gitsamlpe.test.weibo.ui;

import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.touch.OnPressListenerAdapter;
import com.example.wyyu.gitsamlpe.framework.touch.TouchListenerLayout;

/**
 * Created by wyyu on 2018/4/17.
 **/

public class MainBlogActivity extends ToolbarActivity {

    private TouchListenerLayout listenerLayout;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_blog);

        initMainBlogActivity();
    }

    private void initMainBlogActivity() {

        initToolbar("首页", 0xffffffff, 0xff84919b);

        listenerLayout = findViewById(R.id.listener_layout);

        listenerLayout.setOnPressListener(new OnPressListenerAdapter());
    }
}
