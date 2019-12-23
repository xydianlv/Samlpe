package com.example.wyyu.gitsamlpe.test.svga;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import butterknife.OnClick;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2019-12-23.
 **/
public class ActivitySvgAMain extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivitySvgAMain.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg_a_main);

        initToolbar("SvgAMainTest", 0xffffffff, 0xff84919b);
    }

    @OnClick({
        R.id.svg_main_multi, R.id.svg_main_anim, R.id.svg_main_lottie,
    }) public void onEvent(View view) {
        switch (view.getId()) {
            case R.id.svg_main_multi:
                ActivitySvgATest.open(ActivitySvgAMain.this);
                break;
            case R.id.svg_main_anim:
                ActivitySvgAnim.open(ActivitySvgAMain.this);
                break;
            case R.id.svg_main_lottie:
                ActivitySvgLottie.open(ActivitySvgAMain.this);
                break;
        }
    }
}
