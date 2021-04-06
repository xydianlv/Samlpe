package com.example.wyyu.gitsamlpe.test.pager.vertical;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.FragmentPagerAdapter;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2021/3/18.
 **/

public class ActivityPagerVerticalTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityPagerVerticalTest.class));
    }

    @BindView(R.id.vertical_pager) VerticalViewPager viewPager;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_vertival_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("VerticalTest", 0xffffffff, 0xff84919b);

        viewPager.setAdapter(new VerticalPagerAdapter(getSupportFragmentManager(),
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(0);

        viewPager.setPageTransformer(true, new VerticalTransformer());
        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }
}
