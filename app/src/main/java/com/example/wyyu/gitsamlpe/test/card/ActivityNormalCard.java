package com.example.wyyu.gitsamlpe.test.card;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.test.image.matisse.FrescoLoader;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;

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

    private View selectTest;
    private boolean select;

    private View imgTest;
    private boolean selectImg;

    private View vectorTest;
    private boolean selectVector;

    private View playTest;
    private boolean selectPlay;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_card);

        initActivity();
    }

    private void initActivity() {
        initBasicValue();
        loadAnim();
        testSelect();
        imgSelect();
        vectorSelect();
        playSelect();
        playRefresh();
        initWebpTest();
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

    private void testSelect() {
        selectTest = findViewById(R.id.card_select_test);
        selectTest.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                select = !select;
                selectTest.setSelected(select);
            }
        });
        select = false;
    }

    private void imgSelect() {
        imgTest = findViewById(R.id.card_select_img);
        imgTest.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                selectImg = !selectImg;
                imgTest.setSelected(selectImg);
            }
        });
        selectImg = false;
    }

    private void vectorSelect() {
        vectorTest = findViewById(R.id.card_select_vector);
        vectorTest.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                selectVector = !selectVector;
                vectorTest.setSelected(selectVector);
            }
        });
        selectVector = false;
    }

    private void playSelect() {
        playTest = findViewById(R.id.card_play_vector);
        playTest.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                selectPlay = !selectPlay;
                playTest.setSelected(selectPlay);
            }
        });
        selectPlay = false;
    }

    private void playRefresh() {
        ImageView imageView = findViewById(R.id.card_play_refresh);
        imageView.setImageResource(R.drawable.anim_video_refresh);
        Drawable animationDrawable = imageView.getDrawable();
        if (animationDrawable instanceof AnimationDrawable) {
            ((AnimationDrawable) animationDrawable).setOneShot(false);
            ((AnimationDrawable) animationDrawable).start();
        }
    }

    private void initWebpTest() {
        SimpleDraweeView view = findViewById(R.id.card_webp_test);

        FrescoLoader.newFrescoLoader()
            .imageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
            .uri(Uri.parse(
                "android.resource://" + getPackageName() + "/" + R.mipmap.anim_feed_loading))
            .autoPlayAnimation(true)
            .fadeDuration(0)
            .into(view);
    }
}
