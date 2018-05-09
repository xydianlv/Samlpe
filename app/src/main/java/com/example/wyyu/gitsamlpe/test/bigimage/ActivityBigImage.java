package com.example.wyyu.gitsamlpe.test.bigimage;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.bigimage.weight.ImageDraw;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2018/4/12.
 **/

public class ActivityBigImage extends Activity {

    private ActivityImageList.ImageLocationData locationData;
    private ImageDraw imageDraw;

    private List<Animator> animatorList;
    private AnimatorSet animatorSet;

    private float winHeight;
    private float winWidth;

    private AnimatorData animatorData;
    private float scaleData;

    private float pMoveX;
    private float pMoveY;

    private float countY;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initBigImageActivity();
    }

    private void initBigImageActivity() {

        initStaticData();

        initScaleData();

        initAnimData();

        startWelcomeAnimator();
    }

    private void initStaticData() {

        locationData =
            (ActivityImageList.ImageLocationData) getIntent().getSerializableExtra("LocationData");
        imageDraw = findViewById(R.id.check_image_draw);

        imageDraw.setData(locationData);

        animatorList = new ArrayList<>();
        animatorSet = new AnimatorSet();

        winHeight = getWindowManager().getDefaultDisplay().getHeight();
        winWidth = getWindowManager().getDefaultDisplay().getWidth();
    }

    private void initScaleData() {

        int viewHeight = locationData.bottom - locationData.top;
        int viewWidth = locationData.right - locationData.left;

        float heightDelete = winHeight / viewHeight;
        float widthDelete = winWidth / viewWidth;

        scaleData = heightDelete > widthDelete ? widthDelete : heightDelete;
    }

    private void initAnimData() {

        float translateY = winHeight / 2 - (locationData.bottom + locationData.top) / 2;
        float translateX = winWidth / 2 - (locationData.right + locationData.left) / 2;

        animatorData = new AnimatorData(translateX, translateY);

        countY = 0;

        pMoveX = 0;
        pMoveY = 0;
    }

    private void startWelcomeAnimator() {
        setTranslateAnim(animatorData.translateX, animatorData.translateY);
        setScaleAnim(1.0f, scaleData);

        animatorSet.playTogether(animatorList);
        animatorSet.setDuration(200);
        animatorSet.start();

        final float[] alphaValue = new float[1];
        alphaValue[0] = 0.0f;

        final View view = findViewById(R.id.big_image_back);
        view.setAlpha(alphaValue[0]);

        new CountDownTimer(200, 20) {
            @Override public void onTick(long l) {
                alphaValue[0] = alphaValue[0] + 0.12f;
                view.setAlpha(alphaValue[0]);
            }

            @Override public void onFinish() {
                view.setAlpha(1.0f);
            }
        }.start();
    }

    private void setTranslateAnim(float targetTranslateX, float targetTranslateY) {

        ObjectAnimator animatorTranX =
            ObjectAnimator.ofFloat(imageDraw, "translationX", imageDraw.getTranslationX(),
                targetTranslateX);
        ObjectAnimator animatorTranY =
            ObjectAnimator.ofFloat(imageDraw, "translationY", imageDraw.getTranslationY(),
                targetTranslateY);

        animatorList.add(animatorTranX);
        animatorList.add(animatorTranY);
    }

    private void setScaleAnim(float presentScale, float targetScale) {
        ObjectAnimator animatorScaleX =
            ObjectAnimator.ofFloat(imageDraw, "scaleX", presentScale, targetScale);
        ObjectAnimator animatorScaleY =
            ObjectAnimator.ofFloat(imageDraw, "scaleY", presentScale, targetScale);

        animatorList.add(animatorScaleX);
        animatorList.add(animatorScaleY);
    }

    private void refreshScaleAnim(float scaleValue) {

        animatorSet = new AnimatorSet();
        animatorList.clear();

        setScaleAnim(scaleData, scaleData * scaleValue);

        animatorSet.playTogether(animatorList);
        animatorSet.setDuration(200);
        animatorSet.start();
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                pMoveX = event.getRawX();
                pMoveY = event.getRawY();

                countY = 0;

                refreshScaleAnim(0.8f);

                break;
            case MotionEvent.ACTION_MOVE:

                float mMoveX = pMoveX - event.getRawX();
                float mMoveY = pMoveY - event.getRawY();

                pMoveX = pMoveX - mMoveX;
                pMoveY = pMoveY - mMoveY;

                countY = countY + mMoveY;

                findViewById(R.id.big_image_parent).scrollBy((int) mMoveX, (int) mMoveY);

                break;
            case MotionEvent.ACTION_UP:

                if (countY > -200) {
                    refreshScaleAnim(1.0f);
                    findViewById(R.id.big_image_parent).scrollTo(0, 0);
                } else {
                    finish();
                }

                break;
        }
        return super.onTouchEvent(event);
    }

    @Override public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_left_to_mid, R.anim.from_mid_to_right);
    }

    private static class AnimatorData {

        float translateX;
        float translateY;

        AnimatorData(float translateX, float translateY) {
            this.translateX = translateX;
            this.translateY = translateY;
        }
    }
}
