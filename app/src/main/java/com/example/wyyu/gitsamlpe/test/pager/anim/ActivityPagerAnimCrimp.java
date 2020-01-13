package com.example.wyyu.gitsamlpe.test.pager.anim;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2020-01-13.
 **/

public class ActivityPagerAnimCrimp extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityPagerAnimCrimp.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_anim_rotate);

        initActivity();
    }

    private void initActivity() {
        initToolbar("PagerAnimCrimp", 0xffffffff, 0xff84919b);

        ViewPager pagerCube = findViewById(R.id.pager_rotate_view);
        pagerCube.setAdapter(new PagerAnimAdapter(getSupportFragmentManager()));
        pagerCube.setPageTransformer(true, new PageTransformerCrimp());
    }
}
