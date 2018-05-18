package com.example.wyyu.gitsamlpe.test.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/5/7.
 **/

public class PercentProgress extends View {

    public PercentProgress(Context context) {
        super(context);
        initBasicValue(context, null);
    }

    public PercentProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initBasicValue(context, attrs);
    }

    public PercentProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBasicValue(context, attrs);
    }

    // 起始角度
    private int startAngle;
    // 结束角度
    private int endAngle;
    // 圆环宽度
    private float ringWidth;
    // 圆环颜色
    private int ringColor;

    private RectF rectF;
    private Paint paint;

    private void initBasicValue(Context context, AttributeSet attrs) {

        @SuppressLint("Recycle") TypedArray typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.PercentProgress);

        startAngle = typedArray.getInteger(R.styleable.PercentProgress_startAngle, -90);
        endAngle = typedArray.getInteger(R.styleable.PercentProgress_endAngle, 0);
        ringWidth = typedArray.getDimension(R.styleable.PercentProgress_ringWidth, 0);
        ringColor = typedArray.getColor(R.styleable.PercentProgress_ringColor, 0x44242424);

        rectF = new RectF(4, 4, 124, 124);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ringWidth);
        paint.setColor(ringColor);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        resetRectF();
        canvas.drawArc(rectF, startAngle, endAngle, false, paint);
    }

    private void resetRectF() {

        float height = getHeight();
        float width = getWidth();

        rectF.set(ringWidth, ringWidth, width - ringWidth, height - ringWidth);
    }

    // 设置圆环颜色
    public void setRingColor(int ringColor) {
        this.ringColor = ringColor;
        invalidate();
    }

    // 设置圆环宽度
    public void setRingWidth(float ringWidth) {
        this.ringWidth = ringWidth;
        invalidate();
    }

    // 刷新圆环进度
    public void refreshProgress(int progress) {
        endAngle = (int) (360 * (float) progress / 100);
        invalidate();
    }
}
