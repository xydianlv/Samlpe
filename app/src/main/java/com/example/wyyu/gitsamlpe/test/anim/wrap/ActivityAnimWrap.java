package com.example.wyyu.gitsamlpe.test.anim.wrap;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2020-02-08.
 **/

public class ActivityAnimWrap extends FullScreenActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_wrap_height);

        initActivity();
    }

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityAnimWrap.class));
    }

    private void initActivity() {
        View animView = findViewById(R.id.anim_wh_view);

        ViewGroup.LayoutParams params = animView.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(800);

        animator.addUpdateListener(animation -> {
            float percent = (int) animation.getAnimatedValue() * 1.0f / 100;
            params.height = (int) (UIUtils.dpToPx(60.0f) * percent);
            animView.requestLayout();
        });

        findViewById(R.id.anim_wh_btn).setOnClickListener(v -> animator.start());
    }
}
