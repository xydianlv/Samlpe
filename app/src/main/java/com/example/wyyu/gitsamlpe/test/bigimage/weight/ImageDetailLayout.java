package com.example.wyyu.gitsamlpe.test.bigimage.weight;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.CoverFrameLayout;
import com.example.wyyu.gitsamlpe.util.CommonUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2018/5/8.
 **/

public class ImageDetailLayout extends RelativeLayout {

    public ImageDetailLayout(Context context) {
        super(context);
        initImageDetail();
    }

    public ImageDetailLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initImageDetail();
    }

    public ImageDetailLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initImageDetail();
    }

    private CoverFrameLayout layout;
    private ImageView image;

    private void initImageDetail() {

        LayoutInflater.from(getContext()).inflate(R.layout.layout_image_detail, this);

        layout = findViewById(R.id.image_detail_content);
        image = findViewById(R.id.image_detail_main);

        animatorList = new ArrayList<>();
        animatorSet = new AnimatorSet();

        countY = 0;

        pMoveX = 0;
        pMoveY = 0;

        scaleData = 1;

        //int viewHeight = locationData.height - locationData.top;
        //int viewWidth = locationData.width - locationData.left;
        //
        //float heightDelete = winHeight / viewHeight;
        //float widthDelete = winWidth / viewWidth;
        //
        //scaleData = heightDelete > widthDelete ? widthDelete : heightDelete;
    }

    public void setImageDetailValue(int imageId) {
        BitmapFactory.Options options = CommonUtil.getImageValue(imageId);
        layout.setRatioValue(options.outHeight, options.outWidth);
        image.setImageResource(imageId);
    }

    private float pMoveX;
    private float pMoveY;

    private float countY;

    @Override public boolean onTouchEvent(MotionEvent event) {
        //switch (event.getAction()) {
        //    case MotionEvent.ACTION_DOWN:
        //
        //        pMoveX = event.getRawX();
        //        pMoveY = event.getRawY();
        //
        //        countY = 0;
        //
        //        refreshScaleAnim(0.8f);
        //
        //        break;
        //    case MotionEvent.ACTION_MOVE:
        //
        //        float mMoveX = pMoveX - event.getRawX();
        //        float mMoveY = pMoveY - event.getRawY();
        //
        //        pMoveX = pMoveX - mMoveX;
        //        pMoveY = pMoveY - mMoveY;
        //
        //        countY = countY + mMoveY;
        //
        //        layout.scrollBy((int) mMoveX, (int) mMoveY);
        //
        //        break;
        //    case MotionEvent.ACTION_UP:
        //
        //        if (countY > -200) {
        //            refreshScaleAnim(1.0f);
        //            layout.scrollTo(0, 0);
        //        } else {
        //            //finish();
        //        }
        //
        //        break;
        //}
        return super.onTouchEvent(event);
    }

    private List<Animator> animatorList;
    private AnimatorSet animatorSet;
    private float scaleData;

    private void refreshScaleAnim(float scaleValue) {

        animatorSet = new AnimatorSet();
        animatorList.clear();

        setScaleAnim(scaleData, scaleData * scaleValue);

        animatorSet.playTogether(animatorList);
        animatorSet.setDuration(200);
        animatorSet.start();
    }

    private void setScaleAnim(float presentScale, float targetScale) {
        ObjectAnimator animatorScaleX =
            ObjectAnimator.ofFloat(image, "scaleX", presentScale, targetScale);
        ObjectAnimator animatorScaleY =
            ObjectAnimator.ofFloat(image, "scaleY", presentScale, targetScale);

        animatorList.add(animatorScaleX);
        animatorList.add(animatorScaleY);
    }
}
