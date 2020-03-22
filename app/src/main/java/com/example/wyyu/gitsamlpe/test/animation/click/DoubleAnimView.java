package com.example.wyyu.gitsamlpe.test.animation.click;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2020-03-22.
 **/

public class DoubleAnimView extends FrameLayout {

    public DoubleAnimView(@NonNull Context context) {
        super(context);
    }

    public DoubleAnimView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DoubleAnimView(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private static final int[] ICON_ARRAY = new int[] {
        R.mipmap.anim_icon_a, R.mipmap.anim_icon_b, R.mipmap.anim_icon_c, R.mipmap.anim_icon_d,
        R.mipmap.anim_icon_e, R.mipmap.anim_icon_f, R.mipmap.anim_icon_g,
    };

    private static final int[] INDEX_ARRAY = new int[] { 0, 1, 2, 3, 4, 5, 6 };

    private static final int IMG_ANIM = R.mipmap.anim_click_img;

    /**
     * 触屏点在屏幕中的位置
     *
     * @param x x
     * @param y y
     */
    public void onClick(float x, float y) {

    }

    private ImageView loadImage() {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(IMG_ANIM);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));

        return imageView;
    }
}
