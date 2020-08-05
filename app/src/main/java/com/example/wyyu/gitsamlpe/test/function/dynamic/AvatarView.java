package com.example.wyyu.gitsamlpe.test.function.dynamic;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2019/6/10.
 **/

public class AvatarView extends FrameLayout {

    public AvatarView(@NonNull Context context) {
        this(context, null);
    }

    public AvatarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAvatarView();
    }

    private static final float IMG_DIVIDE_FRAME = (float) 31 / 40;

    private View iconContainer;
    private View imgContainer;
    private View frame;

    private void initAvatarView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_avatar_view, this);

        iconContainer = findViewById(R.id.avatar_icon_container);
        imgContainer = findViewById(R.id.avatar_img_container);
        frame = findViewById(R.id.avatar_frame);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        adaptShow(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(heightMeasureSpec));
        //int width = MeasureSpec.getSize(widthMeasureSpec);
        //int height = MeasureSpec.getSize(heightMeasureSpec);
        //setMeasuredDimension(width, height);
    }

    private void adaptShow(int width, int height) {

        ViewGroup.LayoutParams paramsImg = imgContainer.getLayoutParams();

        paramsImg.width = (int) (width * IMG_DIVIDE_FRAME);
        paramsImg.height = (int) (height * IMG_DIVIDE_FRAME);

        imgContainer.setLayoutParams(paramsImg);

        ViewGroup.LayoutParams paramsFrame = frame.getLayoutParams();

        paramsFrame.width = width;
        paramsFrame.height = height;

        frame.setLayoutParams(paramsFrame);

        ViewGroup.LayoutParams paramsIcon = iconContainer.getLayoutParams();

        paramsIcon.width = (int) (width * IMG_DIVIDE_FRAME);
        paramsIcon.height = (int) (height * IMG_DIVIDE_FRAME);

        iconContainer.setLayoutParams(paramsIcon);
    }

    //@Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    //    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //    final int minimumWidth = getSuggestedMinimumWidth();
    //    final int minimumHeight = getSuggestedMinimumHeight();
    //    Log.e("YView", "---minimumWidth = " + minimumWidth + "");
    //    Log.e("YView", "---minimumHeight = " + minimumHeight + "");
    //    int width = measureWidth(minimumWidth, widthMeasureSpec);
    //    int height = measureHeight(minimumHeight, heightMeasureSpec);
    //    Log.e("YView", "---width = " + width + "");
    //    Log.e("YView", "---height = " + height + "");
    //    setMeasuredDimension(width, height);
    //}
    //
    //
    //private int measureWidth(int defaultWidth, int measureSpec) {
    //
    //    int specMode = MeasureSpec.getMode(measureSpec);
    //    int specSize = MeasureSpec.getSize(measureSpec);
    //    Log.e("YViewWidth", "---speSize = " + specSize + "");
    //
    //
    //    switch (specMode) {
    //        case MeasureSpec.AT_MOST:
    //            defaultWidth = getMeasuredWidth();
    //
    //            Log.e("YViewWidth", "---speMode = AT_MOST");
    //            break;
    //        case MeasureSpec.EXACTLY:
    //            Log.e("YViewWidth", "---speMode = EXACTLY");
    //            defaultWidth = specSize;
    //            break;
    //        case MeasureSpec.UNSPECIFIED:
    //            Log.e("YViewWidth", "---speMode = UNSPECIFIED");
    //            defaultWidth = Math.max(defaultWidth, specSize);
    //    }
    //    return defaultWidth;
    //}
    //
    //private int measureHeight(int defaultHeight, int measureSpec) {
    //
    //    int specMode = MeasureSpec.getMode(measureSpec);
    //    int specSize = MeasureSpec.getSize(measureSpec);
    //    Log.e("YViewHeight", "---speSize = " + specSize + "");
    //
    //    switch (specMode) {
    //        case MeasureSpec.AT_MOST:
    //            defaultHeight = getMeasuredHeight();
    //            Log.e("YViewHeight", "---speMode = AT_MOST");
    //            break;
    //        case MeasureSpec.EXACTLY:
    //            defaultHeight = specSize;
    //            Log.e("YViewHeight", "---speSize = EXACTLY");
    //            break;
    //        case MeasureSpec.UNSPECIFIED:
    //            defaultHeight = Math.max(defaultHeight, specSize);
    //            Log.e("YViewHeight", "---speSize = UNSPECIFIED");
    //            break;
    //    }
    //    return defaultHeight;
    //}

    //斗志燃，故地墙畔，封残垣
    //当立断，破壁凿岩，擒叛蛮
    //
    //
    //
    //艾伦堵门
    //    阿尔敏智取莱纳
    //艾伦大战凯巨
    //    雷枪奇袭莱纳
    //汉吉雷枪
    //    贝尔托莱炸街
    //兵长砍猴
    //    阿尔敏抗伤
    //艾伦砍超巨
    //    团长战斗

}
