package com.example.wyyu.gitsamlpe.test.animation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2019/2/18.
 **/

public class ActivitySpringAnimation extends FullScreenActivity implements View.OnClickListener {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivitySpringAnimation.class));
    }

    private SpringAnimation animA;
    private SpringAnimation animB;

    private View animView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring_animation);

        initActivity();
    }

    private void initActivity() {
        initBasicView();
        initAnimationValue();
    }

    private void initBasicView() {
        animView = findViewById(R.id.spring_anim_view);

        findViewById(R.id.spring_anim_bt_1).setOnClickListener(this);
    }

    private void initAnimationValue() {
        // 初始化 SpringForce，初始化参数为动画结束位置
        SpringForce forceA = new SpringForce(320);
        // 设置弹性阻尼，参数值越大，摆动次数越少，取 1 时完全不摆动
        forceA.setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
        // 弹性的生硬度，值越小，弹簧越容易摆动，摆动时间越长
        forceA.setStiffness(SpringForce.STIFFNESS_LOW);

        animA = new SpringAnimation(animView, SpringAnimation.TRANSLATION_Y).setSpring(forceA);
        // 动画起始速度
        animA.setStartVelocity(4.0f);
        // 设置动画起始位置
        animA.setStartValue(-10.0f);

        SpringForce forceB = new SpringForce(4.0f);
        forceB.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        forceB.setStiffness(0.1f);

        animB = new SpringAnimation(animView, SpringAnimation.SCALE_X).setSpring(forceB);
        animB.setStartVelocity(2.0f);
        animB.setStartValue(0.0f);
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spring_anim_bt_1:
                animB.cancel();
                animA.cancel();
                animA.start();
                break;
            case R.id.spring_anim_bt_2:
                animA.cancel();
                animB.cancel();
                animB.start();
                break;
        }
    }
}
