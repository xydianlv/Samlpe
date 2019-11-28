package com.example.wyyu.gitsamlpe.test.animation;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import java.text.MessageFormat;

/**
 * Created by wyyu on 2019/3/7.
 **/

public class ActivityBezier extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityBezier.class));
    }

    private View startView;
    private View endView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);

        initActivity();
    }

    private void initActivity() {
        startView = findViewById(R.id.bezier_start);
        endView = findViewById(R.id.bezier_end);

        findViewById(R.id.bezier_click).setOnClickListener(v -> showBezierAnim());

        showLocationValue();
    }

    private void showBezierAnim() {
        int[] locationStart = new int[2];
        startView.getLocationOnScreen(locationStart);

        int[] locationEnd = new int[2];
        endView.getLocationOnScreen(locationEnd);

        final Point pointS = new Point(locationStart[0], locationStart[1]);
        final Point pointE = new Point(locationEnd[0], locationEnd[1]);
        final Point pointM =
            new Point((locationStart[0] + locationEnd[0]) / 2, locationStart[0] - 160);

        ObjectAnimator animator = new ObjectAnimator();
        animator.setFloatValues(0.0f, 1.0f);
        animator.setDuration(5000);
        animator.addUpdateListener(animation -> {
            float t = (float) animation.getAnimatedValue();
            float m = 1.0f - t;

            float x = m * m * pointS.x + 2 * t * m * pointM.x + t * t * pointE.x;
            float y = m * m * pointS.y + 2 * t * m * pointM.y + t * t * pointE.y;

            Log.e("ActivityBezier", "t : " + t + "  m : " + m + "  x : " + x + "  y : " + y);

            startView.setX(x);
            startView.setY(y);
        });
        animator.setTarget(1.0f);
        animator.start();
    }

    private void showLocationValue() {
        final TextView textView = findViewById(R.id.bezier_location_value);
        final View view = findViewById(R.id.bezier_location);

        view.setOnClickListener(v -> {
            int[] location = new int[2];
            view.getLocationOnScreen(location);

            textView.setText(MessageFormat.format("LocationValue -> x : {0}  y : {1}", location[0],
                location[1]));
        });
    }
}
