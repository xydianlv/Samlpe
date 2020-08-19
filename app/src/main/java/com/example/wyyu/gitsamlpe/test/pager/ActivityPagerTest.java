package com.example.wyyu.gitsamlpe.test.pager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;
import com.example.wyyu.gitsamlpe.test.pager.blog.ActivityBlogTest;
import com.example.wyyu.gitsamlpe.test.pager.complex.ActivityPagerComplexTest;
import com.example.wyyu.gitsamlpe.test.pager.life.ActivityPagerLifeTest;
import com.example.wyyu.gitsamlpe.test.pager.slide.ActivitySlideTest;
import com.example.wyyu.gitsamlpe.test.pager.snap.ActivityPagerSnapTest;
import com.example.wyyu.gitsamlpe.test.pager.transform.ActivityPagerTransformTest;
import com.example.wyyu.gitsamlpe.test.pager.banner.ActivityBannerTest;
import com.example.wyyu.gitsamlpe.test.pager.ultra.ActivityUltraTest;

/**
 * Created by wyyu on 2018/6/29.
 **/

public class ActivityPagerTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityPagerTest.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("PagerTest", 0xffffffff, 0xff84919b);

        ListViewMain viewMain = findViewById(R.id.pager_test_list);

        viewMain.addItem("Ultra", v -> ActivityUltraTest.open(ActivityPagerTest.this))
            .addItem("Banner", v -> ActivityBannerTest.open(ActivityPagerTest.this))
            .addItem("Snap", v -> ActivityPagerSnapTest.open(ActivityPagerTest.this))
            .addItem("Transform", v -> ActivityPagerTransformTest.open(ActivityPagerTest.this))
            .addItem("Complex", v -> ActivityPagerComplexTest.open(ActivityPagerTest.this))
            .addItem("Slide", v -> ActivitySlideTest.open(ActivityPagerTest.this))
            .addItem("Blog", v -> ActivityBlogTest.open(ActivityPagerTest.this))
            .addItem("Life", v -> ActivityPagerLifeTest.open(ActivityPagerTest.this))
            .refreshList();
    }
}
