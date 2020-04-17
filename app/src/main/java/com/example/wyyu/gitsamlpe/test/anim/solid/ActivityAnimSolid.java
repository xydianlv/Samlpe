package com.example.wyyu.gitsamlpe.test.anim.solid;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2020-04-17.
 **/

public class ActivityAnimSolid extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityAnimSolid.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_solid);

        initActivity();
    }

    private void initActivity() {
        initRotateX();
    }

    private void initRotateX() {

        View rotateX = findViewById(R.id.anim_solid_x);

        ObjectAnimator animShow = ObjectAnimator.ofFloat(rotateX, "rotationX", 0, 180);
        animShow.setInterpolator(new LinearInterpolator());
        animShow.setDuration(600);
        ObjectAnimator animHide = ObjectAnimator.ofFloat(rotateX, "rotationX", 180, 360);
        animHide.setInterpolator(new LinearInterpolator());
        animHide.setDuration(600);

        int[] animValue = new int[] { 0 };

        rotateX.setOnClickListener(v -> {
            if (animValue[0] == 0) {
                animValue[0] = 1;
                animShow.start();
            } else {
                animValue[0] = 0;
                animHide.start();
            }
        });
    }
}
