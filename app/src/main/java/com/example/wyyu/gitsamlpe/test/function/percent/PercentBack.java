package com.example.wyyu.gitsamlpe.test.function.percent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2019/2/20.
 **/

public class PercentBack extends View {

    public PercentBack(Context context) {
        this(context, null);
    }

    public PercentBack(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentBack(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPercentCore();
    }

    private static final int CIRCLE_VALUE = UIUtils.dpToPx(5);

    // 控件在布局中的高度
    private int height;
    // 控件在布局中的宽度
    private int width;

    // 选中区域的宽度占比
    private float selectPercent;
    // 选中区域的颜色值
    private int colorSelect;
    // 圆角度数
    private int circleValue;
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

        circleValue = CIRCLE_VALUE > height / 2 ? height / 2 : CIRCLE_VALUE;

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override protected void onDraw(Canvas canvas) {
        drawPercent(canvas);
        //if (selectPercent == 0.0f) {
        //    drawFilledBackPercent(canvas, colorBack);
        //} else if (selectPercent == 1.0f) {
        //    drawFilledBackPercent(canvas, colorSelect);
        //} else {
        //    drawSelectPercent(canvas);
        //}
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

    private void drawPercent(Canvas canvas) {
        paintSelect.setColor(colorSelect);
        paintBack.setColor(colorBack);

        RectF rectBack = new RectF(circleValue, 0, width - circleValue, height);
        canvas.drawRect(rectBack, paintBack);

        RectF roundRightTop = new RectF(width - circleValue * 2, 0, width, circleValue * 2);
        canvas.drawArc(roundRightTop, -90, 90, true, paintBack);

        RectF elementRight =
            new RectF(width - circleValue, circleValue, width, height - circleValue);
        canvas.drawRect(elementRight, paintBack);

        RectF roundRightBottom =
            new RectF(width - circleValue * 2, height - circleValue * 2, width, height);
        canvas.drawArc(roundRightBottom, 0, 90, true, paintBack);

        RectF roundLeftTop = new RectF(0, 0, circleValue * 2, circleValue * 2);
        canvas.drawArc(roundLeftTop, 180, 90, true, paintSelect);

        RectF elementLeft = new RectF(0, circleValue, circleValue, height - circleValue);
        canvas.drawRect(elementLeft, paintSelect);

        RectF roundLeftBottom = new RectF(0, height - circleValue * 2, circleValue * 2, height);
        canvas.drawArc(roundLeftBottom, 90, 90, true, paintSelect);

        if (selectPercent <= 0.0f) {
            return;
        }
        RectF rectSelect =
            new RectF(circleValue, 0, selectPercent * (width - circleValue * 2), height);
        canvas.drawRect(rectSelect, paintSelect);
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
