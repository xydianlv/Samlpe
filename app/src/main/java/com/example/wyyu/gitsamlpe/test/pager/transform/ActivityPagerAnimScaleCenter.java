package com.example.wyyu.gitsamlpe.test.pager.transform;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2020-01-13.
 **/

public class ActivityPagerAnimScaleCenter extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityPagerAnimScaleCenter.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_anim_scale_center);

        initActivity();
    }

    private void initActivity() {
        initToolbar("PagerAnimScaleCenter", 0xffffffff, 0xff84919b);

        ViewPager pagerCube = findViewById(R.id.pager_scale_center_view);
        pagerCube.setAdapter(new PagerAnimAdapter(getSupportFragmentManager()));
        pagerCube.setPageTransformer(true, new PageTransformerScaleCenter());
    }
}
