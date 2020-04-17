package com.example.wyyu.gitsamlpe.test.anim.vector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2020-04-17.
 **/

public class ActivityAnimVector extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityAnimVector.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_anim);

        initActivity();
    }

    private void initActivity() {
        initSysAnim();
        initSelectViewAnim();
        initSelectImgAnim();
        initSelectVectorAnim();
        initSelectPlayAnim();
    }

    private void initSysAnim() {
        AnimationSet setStartT =
            (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.anim_set_r_a_9_1);
        AnimationSet setStartB =
            (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.anim_set_r_a_9_0);
        AnimationSet setBackT =
            (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.anim_set_r_a_0_1);
        AnimationSet setBackB =
            (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.anim_set_r_a_0_0);

        View animBottom = findViewById(R.id.vector_anim_test_bottom);
        View animTop = findViewById(R.id.vector_anim_test_top);

        int[] onAnim = new int[] { 0 };

        findViewById(R.id.vector_anim_click).setOnClickListener(v -> {
            if (onAnim[0] == 0) {
                animTop.startAnimation(setBackT);
                animBottom.startAnimation(setBackB);
                onAnim[0] = 1;
            } else {
                animTop.startAnimation(setStartT);
                animBottom.startAnimation(setStartB);
                onAnim[0] = 0;
            }
        });
    }

    private void initSelectViewAnim() {
        View selectView = findViewById(R.id.vector_anim_select_test);
        int[] onAnim = new int[] { 0 };

        selectView.setOnClickListener(v -> {
            onAnim[0] = onAnim[0] == 0 ? 1 : 0;
            selectView.setSelected(onAnim[0] == 1);
        });
    }

    private void initSelectImgAnim() {
        View selectImg = findViewById(R.id.vector_anim_select_img);
        int[] onAnim = new int[] { 0 };

        selectImg.setOnClickListener(v -> {
            onAnim[0] = onAnim[0] == 0 ? 1 : 0;
            selectImg.setSelected(onAnim[0] == 1);
        });
    }

    private void initSelectVectorAnim() {
        View selectVector = findViewById(R.id.vector_anim_select_vector);
        int[] onAnim = new int[] { 0 };

        selectVector.setOnClickListener(v -> {
            onAnim[0] = onAnim[0] == 0 ? 1 : 0;
            selectVector.setSelected(onAnim[0] == 1);
        });
    }

    private void initSelectPlayAnim() {
        View selectPlay = findViewById(R.id.vector_anim_select_play);
        int[] onAnim = new int[] { 0 };

        selectPlay.setOnClickListener(v -> {
            onAnim[0] = onAnim[0] == 0 ? 1 : 0;
            selectPlay.setSelected(onAnim[0] == 1);
        });
    }
}
