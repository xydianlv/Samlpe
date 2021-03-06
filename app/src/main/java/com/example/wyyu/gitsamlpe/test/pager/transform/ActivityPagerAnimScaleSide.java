package com.example.wyyu.gitsamlpe.test.pager.transform;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2020-01-13.
 **/

public class ActivityPagerAnimScaleSide extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityPagerAnimScaleSide.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_anim_scale_side);

        initActivity();
    }

    private void initActivity() {
        initToolbar("PagerAnimScaleSide", 0xffffffff, 0xff84919b);

        ViewPager pagerCube = findViewById(R.id.pager_scale_side_view);
        pagerCube.setAdapter(new PagerAnimAdapter(getSupportFragmentManager()));
        pagerCube.setPageTransformer(true, new PageTransformerScaleSide());
    }
}
