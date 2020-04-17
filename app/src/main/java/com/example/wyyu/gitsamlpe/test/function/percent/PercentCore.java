package com.example.wyyu.gitsamlpe.test.function.percent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wyyu on 2019/2/18.
 *
 * 两侧的半圆弧不要纳入百分比计算范围就好了
 **/

public class PercentCore extends View {

    public PercentCore(Context context) {
        this(context, null);
    }

    public PercentCore(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentCore(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPercentCore();
    }

    // 控件在布局中的高度
    private float height;
    // 控件在布局中的宽度
    private float width;

    // 选中区域的宽度占比
    private float selectPercent;
    // 选中区域的颜色值
    private int colorSelect;
    // 背景颜色
    private int colorBack;

    private Paint paintSelect;
    private Paint paintBack;

    private void initPercentCore() {

        paintSelect = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBack = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        height = MeasureSpec.getSize(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override protected void onDraw(Canvas canvas) {
        if (selectPercent == 0.0f) {
            drawFilledBackPercent(canvas, colorBack);
        } else if (selectPercent == 1.0f) {
            drawFilledBackPercent(canvas, colorSelect);
        } else {
            drawSelectPercent(canvas);
        }
        super.onDraw(canvas);
    }

    private void drawFilledBackPercent(Canvas canvas, int colorBack) {
        paintBack.setColor(colorBack);
        RectF rectF = new RectF(0.0f, 0.0f, width, height);
        canvas.drawRoundRect(rectF, height / 2, height / 2, paintBack);
    }

    private void drawSelectPercent(Canvas canvas) {
        paintSelect.setColor(colorSelect);
        paintBack.setColor(colorBack);

        RectF rectBack = new RectF(height / 2, 0, width - height / 2, height);
        RectF rectSelect = new RectF(height / 2, 0, selectPercent * width - height / 2, height);
        RectF roundRight = new RectF(width - height, 0, width, height);
        RectF roundLeft = new RectF(0, 0, height, height);

        canvas.drawRect(rectBack, paintBack);
        canvas.drawRect(rectSelect, paintSelect);
        canvas.drawArc(roundRight, -90, 180, true, paintBack);
        canvas.drawArc(roundLeft, 90, 180, true, paintSelect);
    }

    /**
     * 为百分比控件设置显示参数
     *
     * @param percent 选中的百分比
     * @param selectColor 选中部分的颜色显示
     * @param backColor 背景颜色
     */
    public void setPercentValue(float percent, int selectColor, int backColor) {
        this.selectPercent = percent;
        this.colorSelect = selectColor;
        this.colorBack = backColor;

        invalidate();
    }
}
