package com.example.wyyu.gitsamlpe.test.pager.complex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.pager.transform.PageTransformerCube;

/**
 * Created by wyyu on 2020-01-15.
 **/

public class ActivityPagerComplexTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityPagerComplexTest.class));
    }

    @BindView(R.id.complex_pager_view) ViewPager viewPager;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complex_pager);

        initActivity();
    }

    private void initActivity() {
        initToolbar("ComplexPage", 0xffffffff, 0xff84919b);

        viewPager.setAdapter(new ComplexFragAdapter(getSupportFragmentManager()));
        viewPager.setPageTransformer(true, new PageTransformerCube());
        viewPager.setOffscreenPageLimit(2);
    }
}
