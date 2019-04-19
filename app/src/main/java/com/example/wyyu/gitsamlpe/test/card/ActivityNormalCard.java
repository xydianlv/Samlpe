package com.example.wyyu.gitsamlpe.test.card;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2018/6/23.
 **/

public class ActivityNormalCard extends FullScreenActivity {

    private AnimationSet setStartT;
    private AnimationSet setStartB;
    private AnimationSet setBackT;
    private AnimationSet setBackB;

    private View animViewT;
    private View animViewB;

    private boolean showB;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_card);

        initActivity();
    }

    private void initActivity() {
        initBasicValue();
        loadAnim();
    }

    private void initBasicValue() {
        animViewT = findViewById(R.id.card_anim_test_t);
        animViewB = findViewById(R.id.card_anim_test_b);

        findViewById(R.id.card_anim_click).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (showB) {
                    animViewT.startAnimation(setBackT);
                    animViewB.startAnimation(setBackB);
                    showB = false;
                } else {
                    animViewT.startAnimation(setStartT);
                    animViewB.startAnimation(setStartB);
                    showB = true;
                }
            }
        });
    }

    private void loadAnim() {
        setStartT = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.anim_set_r_a_9_1);
        setStartB = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.anim_set_r_a_9_0);
        setBackT = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.anim_set_r_a_0_1);
        setBackB = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.anim_set_r_a_0_0);

        showB = false;
    }
}
