package com.example.wyyu.gitsamlpe.test.bezier.anim;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2020-04-20.
 **/

public class ActivityBezierAnim extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityBezierAnim.class));
    }

    @BindView(R.id.bezier_anim_start) View viewStart;
    @BindView(R.id.bezier_anim_end) View viewEnd;

    private int[] locationStart;
    private int[] locationEnd;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_anim);

        initActivity();
    }

    private void initActivity() {
        initValue();
        initClick();
    }

    private void initValue() {
        locationStart = null;
        locationEnd = null;
    }

    private void initClick() {
        findViewById(R.id.bezier_anim_btn).setOnClickListener(v -> {
            if (locationStart == null) {
                locationStart = new int[2];
                viewStart.getLocationOnScreen(locationStart);
            }
            if (locationEnd == null) {
                locationEnd = new int[2];
                viewEnd.getLocationOnScreen(locationEnd);
            }
            showBezierAnim();
        });
    }

    private void showBezierAnim() {

        final Point pointS = new Point(locationStart[0],
            locationStart[1] - UIUtils.getStatusHeightByDimen(ActivityBezierAnim.this));
        final Point pointE = new Point(locationEnd[0], locationEnd[1]);
        final Point pointM =
            new Point((locationStart[0] + locationEnd[0]) / 2, locationStart[0] - 160);

        ObjectAnimator animator = new ObjectAnimator();
        animator.setFloatValues(0.0f, 1.0f);
        animator.setDuration(1000);
        animator.addUpdateListener(animation -> {
            float t = (float) animation.getAnimatedValue();
            float m = 1.0f - t;

            float x = m * m * pointS.x + 2 * t * m * pointM.x + t * t * pointE.x;
            float y = m * m * pointS.y + 2 * t * m * pointM.y + t * t * pointE.y;

            viewStart.setX(x);
            viewStart.setY(y);
        });
        animator.setTarget(1.0f);
        animator.start();
    }
}
