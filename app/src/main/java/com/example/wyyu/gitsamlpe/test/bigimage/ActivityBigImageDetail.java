package com.example.wyyu.gitsamlpe.test.bigimage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.bigimage.data.AnimatorData;
import com.example.wyyu.gitsamlpe.test.bigimage.data.LocationData;
import com.example.wyyu.gitsamlpe.test.bigimage.data.LocationObservable;
import com.example.wyyu.gitsamlpe.test.bigimage.data.OnConfirmPositionCallBack;
import com.example.wyyu.gitsamlpe.test.bigimage.data.PositionObservable;
import com.example.wyyu.gitsamlpe.test.bigimage.weight.ImageContainer;
import com.example.wyyu.gitsamlpe.test.bigimage.weight.ImageFunView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2018/5/8.
 **/

public class ActivityBigImageDetail extends Activity implements OnConfirmPositionCallBack {

    private static final String KEY_IMAGE_LOCATION_DATA = "key_image_location_data";
    private static final String KEY_IMAGE_RES_LIST = "key_image_res_list";
    private static final String KEY_IMAGE_INDEX = "key_image_index";

    public static void open(Context context, LocationData locationData, int[] resList, int index) {
        Intent intent = new Intent(context, ActivityBigImageDetail.class);
        intent.putExtra(KEY_IMAGE_LOCATION_DATA, locationData);
        intent.putExtra(KEY_IMAGE_RES_LIST, resList);
        intent.putExtra(KEY_IMAGE_INDEX, index);
        context.startActivity(intent);
    }

    private LocationData locationData;
    private int[] imageArray;
    private int index;

    private ImageFunView imageFunView;
    private ViewPager viewPager;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image_detail);

        LocationObservable.getInstance().attach(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        initBigImageDetail();
    }

    @Override protected void onDestroy() {
        LocationObservable.getInstance().detach(this);
        super.onDestroy();
    }

    private void initBigImageDetail() {
        initBasicValue();
        initViewPager();

        initStaticData();

        initScaleData();

        initAnimData();

        startWelcomeAnimator();
    }

    private void initBasicValue() {

        locationData = (LocationData) getIntent().getSerializableExtra(KEY_IMAGE_LOCATION_DATA);
        imageArray = getIntent().getIntArrayExtra(KEY_IMAGE_RES_LIST);
        index = getIntent().getIntExtra(KEY_IMAGE_INDEX, 0);

        imageFunView = findViewById(R.id.big_image_fun_view);
    }

    private void initViewPager() {

        viewPager = findViewById(R.id.big_image_pager);

        viewPager.setAdapter(new ViewPagerAdapter(this, imageArray));
        viewPager.setOffscreenPageLimit(imageArray.length - 1);
        viewPager.setCurrentItem(index);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset,
                int positionOffsetPixels) {

            }

            @Override public void onPageSelected(int position) {
                PositionObservable.getInstance().positionUpdate(position);
                index = position;
            }

            @Override public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private List<Animator> animatorList;
    private AnimatorSet animatorSet;

    private float winHeight;
    private float winWidth;

    private AnimatorData animatorData;
    private float scaleData;

    //private float pMoveX;
    //private float pMoveY;
    //
    //private float countY;

    private void initStaticData() {

        animatorList = new ArrayList<>();
        animatorSet = new AnimatorSet();

        winHeight = getWindowManager().getDefaultDisplay().getHeight();
        winWidth = getWindowManager().getDefaultDisplay().getWidth();

        imageFunView.setImageResource(imageArray[index]);
        imageFunView.setData(locationData);
        viewPager.setVisibility(View.GONE);
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

        //countY = 0;
        //
        //pMoveX = 0;
        //pMoveY = 0;
    }

    private void startWelcomeAnimator() {
        setTranslateAnim(animatorData.translateX, animatorData.translateY);
        setScaleAnim(1.0f, scaleData);

        animatorSet.playTogether(animatorList);
        animatorSet.setDuration(200);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                viewPager.setVisibility(View.VISIBLE);
                imageFunView.setVisibility(View.GONE);
            }
        });
    }

    private void setTranslateAnim(float targetTranslateX, float targetTranslateY) {

        ObjectAnimator animatorTranX =
            ObjectAnimator.ofFloat(imageFunView, "translationX", imageFunView.getTranslationX(),
                targetTranslateX);
        ObjectAnimator animatorTranY =
            ObjectAnimator.ofFloat(imageFunView, "translationY", imageFunView.getTranslationY(),
                targetTranslateY);

        animatorList.add(animatorTranX);
        animatorList.add(animatorTranY);
    }

    private void setScaleAnim(float presentScale, float targetScale) {
        ObjectAnimator animatorScaleX =
            ObjectAnimator.ofFloat(imageFunView, "scaleX", presentScale, targetScale);
        ObjectAnimator animatorScaleY =
            ObjectAnimator.ofFloat(imageFunView, "scaleY", presentScale, targetScale);

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

    @Override public void callBack(LocationData locationData) {
        this.locationData = locationData;
    }

    private static class ViewPagerAdapter extends PagerAdapter {

        private List<ImageContainer> layoutList;

        private int[] imageArray;

        ViewPagerAdapter(Activity activity, int[] imageArray) {
            this.layoutList = new ArrayList<>(imageArray.length);
            this.imageArray = imageArray;

            for (int imageId : imageArray) {
                ImageContainer detailLayout = new ImageContainer(activity);
                detailLayout.setImageValue(imageId);
                layoutList.add(detailLayout);
            }
        }

        @Override public int getCount() {
            return imageArray == null ? 0 : imageArray.length;
        }

        @Override public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override public Object instantiateItem(ViewGroup container, int position) {
            container.addView(layoutList.get(position));
            return layoutList.get(position);
        }
    }

    private void onFinish() {
        imageFunView.setImageResource(imageArray[index]);
        imageFunView.setData(locationData);
        imageFunView.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.GONE);

        resetScaleData();
        resetAnimData();
    }

    private void resetScaleData() {

        int viewHeight = locationData.bottom - locationData.top;
        int viewWidth = locationData.right - locationData.left;

        float heightDelete = winHeight / viewHeight;
        float widthDelete = winWidth / viewWidth;

        scaleData = heightDelete > widthDelete ? widthDelete : heightDelete;
    }

    private void resetAnimData() {

        float translateY = winHeight / 2 - (locationData.bottom + locationData.top) / 2;
        float translateX = winWidth / 2 - (locationData.right + locationData.left) / 2;

        //animatorData = new AnimatorData(translateX, translateY);

        startFinishAnimator(translateX, translateY);

        //countY = 0;
        //
        //pMoveX = 0;
        //pMoveY = 0;
    }

    private void startFinishAnimator(float targetTranslateX, float targetTranslateY) {

        ObjectAnimator animatorTranX =
            ObjectAnimator.ofFloat(imageFunView, "translationX", targetTranslateX, 0);
        ObjectAnimator animatorTranY =
            ObjectAnimator.ofFloat(imageFunView, "translationY", targetTranslateY, 0);

        animatorList.add(animatorTranX);
        animatorList.add(animatorTranY);

        setScaleAnim(scaleData, 1.0f);

        animatorSet.playTogether(animatorList);
        animatorSet.setDuration(200);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                findViewById(R.id.big_image_root).setVisibility(View.GONE);
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onFinish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
