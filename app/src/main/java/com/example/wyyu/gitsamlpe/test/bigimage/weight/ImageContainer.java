package com.example.wyyu.gitsamlpe.test.bigimage.weight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;

/**
 * Created by wyyu on 2018/5/8.
 **/

public class ImageContainer extends FrameLayout {

    public ImageContainer(@NonNull Context context) {
        super(context);
        initContainer();
    }

    public ImageContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initContainer();
    }

    public ImageContainer(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initContainer();
    }

    private ImageView imageView;

    private void initContainer() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_image_container, this);
        imageView = findViewById(R.id.image_container_main);
    }

    public void setImageValue(int imageId) {
        imageView.setImageResource(imageId);
    }

    //private float pMoveX = 0;
    //private float pMoveY = 0;
    //
    //private float countY = 0;

    //@Override public boolean onTouchEvent(MotionEvent event) {
    //
    //    switch (event.getAction()) {
    //        case MotionEvent.ACTION_DOWN:
    //
    //            pMoveX = event.getRawX();
    //            pMoveY = event.getRawY();
    //
    //            countY = 0;
    //
    //            //refreshScaleAnim(0.8f);
    //
    //            break;
    //        case MotionEvent.ACTION_MOVE:
    //
    //            float mMoveX = pMoveX - event.getRawX();
    //            float mMoveY = pMoveY - event.getRawY();
    //
    //            pMoveX = pMoveX - mMoveX;
    //            pMoveY = pMoveY - mMoveY;
    //
    //            countY = countY + mMoveY;
    //
    //            imageView.setScaleX(0.8f);
    //            imageView.setScaleY(0.8f);
    //
    //            ULog.show("ImageContainer -> countY : " + countY);
    //
    //            //findViewById(R.id.big_image_parent).scrollBy((int) mMoveX, (int) mMoveY);
    //
    //            break;
    //        case MotionEvent.ACTION_UP:
    //
    //            //if (countY > -200) {
    //            //    refreshScaleAnim(1.0f);
    //            //    findViewById(R.id.big_image_parent).scrollTo(0, 0);
    //            //} else {
    //            //    finish();
    //            //}
    //
    //            break;
    //    }
    //
    //    return super.onTouchEvent(event);
    //}
}
