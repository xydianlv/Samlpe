package com.example.wyyu.gitsamlpe.framework;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/5/7.
 **/

public class CoverFrameLayout extends FrameLayout {

    public CoverFrameLayout(Context context) {
        super(context);
        initCoverFrameLayout(context, null);
    }

    public CoverFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCoverFrameLayout(context, attrs);
    }

    public CoverFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCoverFrameLayout(context, attrs);
    }

    // 高宽比
    private float heightWidthRatio;
    // 参与比例计算的宽度值
    private float ratioWidth;
    // 参与比例计算的高度值
    private float ratioHeight;

    private void initCoverFrameLayout(Context context, AttributeSet attrs) {
        @SuppressLint("Recycle") TypedArray typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CoverFrameLayout);

        heightWidthRatio = typedArray.getFloat(R.styleable.CoverFrameLayout_heightWidthRatio, 0);
        ratioHeight = typedArray.getFloat(R.styleable.CoverFrameLayout_ratioHeight, 0);
        ratioWidth = typedArray.getFloat(R.styleable.CoverFrameLayout_ratioWidth, 0);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        if (heightWidthRatio != 0) {
            setMeasuredDimension(width, (int) (width * heightWidthRatio));
        } else if (ratioHeight != 0 && ratioWidth != 0) {
            setMeasuredDimension(width, (int) ((float) width * (ratioHeight / ratioWidth)));
        }
    }

    public void setHeightWidthRatio(float heightWidthRatio) {
        this.heightWidthRatio = heightWidthRatio;
        invalidate();
    }

    public void setRatioValue(float ratioHeight, float ratioWidth) {
        this.ratioHeight = ratioHeight;
        this.ratioWidth = ratioWidth;
        invalidate();
    }
}
