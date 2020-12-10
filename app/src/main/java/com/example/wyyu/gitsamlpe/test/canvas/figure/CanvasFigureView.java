package com.example.wyyu.gitsamlpe.test.canvas.figure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2020-01-09.
 *
 * 控件尺寸高为 200，绘制的图形尺寸采用 80*80
 **/

public class CanvasFigureView extends View {

    public CanvasFigureView(Context context) {
        super(context);
        initValue();
    }

    public CanvasFigureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initValue();
    }

    public CanvasFigureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initValue();
    }

    private static final float SIZE_DEFAULT = UIUtils.dpToPx(80.0f);
    private static final float SIZE_DIVIDE = UIUtils.dpToPx(10.0f);
    private static final float SIZE_VIEW = UIUtils.dpToPx(100.0f);

    private LinearGradient gradient;
    private Paint paint;

    private RectF rectFA;
    private RectF rectFB;
    private RectF rectFC;
    private RectF rectFD;
    private RectF rectFE;
    private RectF rectFF;

    private void initValue() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xfff44336);

        gradient = new LinearGradient(0.0f, 0.0f, 100.0f, 0.0f, 0x55ff0000,
            0x5500ff00, Shader.TileMode.CLAMP);

        rectFA = new RectF(SIZE_DIVIDE, SIZE_DIVIDE + SIZE_VIEW, SIZE_VIEW - SIZE_DIVIDE,
            SIZE_VIEW * 2 - SIZE_DIVIDE);
        rectFB =
            new RectF(SIZE_VIEW + SIZE_DIVIDE, SIZE_DIVIDE + SIZE_VIEW, SIZE_VIEW * 2 - SIZE_DIVIDE,
                SIZE_VIEW * 2 - SIZE_DIVIDE);

        rectFC = new RectF(SIZE_DIVIDE, SIZE_DIVIDE + SIZE_VIEW * 2, SIZE_VIEW - SIZE_DIVIDE,
            SIZE_VIEW * 3 - SIZE_DIVIDE);

        rectFD = new RectF(SIZE_DIVIDE, SIZE_DIVIDE + SIZE_VIEW * 3, SIZE_VIEW * 2 - SIZE_DIVIDE,
            SIZE_VIEW * 4 - SIZE_DIVIDE);

        rectFE = new RectF(SIZE_DIVIDE, SIZE_DIVIDE + SIZE_VIEW * 4, SIZE_VIEW * 2 - SIZE_DIVIDE,
            SIZE_VIEW * 5 - SIZE_DIVIDE);

        rectFF = new RectF(SIZE_DIVIDE, SIZE_DIVIDE + SIZE_VIEW * 5, SIZE_VIEW * 2 - SIZE_DIVIDE,
            SIZE_VIEW * 6 - SIZE_DIVIDE);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画一个圆
        canvas.drawCircle(SIZE_VIEW / 2, SIZE_VIEW / 2, SIZE_DEFAULT / 2, paint);

        // 画一个扇形
        canvas.drawArc(rectFA, -90.0f, 225.0f, true, paint);

        // 画一个首尾相连的扇形
        canvas.drawArc(rectFB, -90.0f, 225.0f, false, paint);

        // 画一个正方形
        canvas.drawRect(rectFC, paint);

        // 画一个椭圆
        canvas.drawOval(rectFD, paint);

        // 画一个圆角矩形
        canvas.drawRoundRect(rectFE, 30, 30, paint);

        // 画一个正方形
        paint.setShader(gradient);
        canvas.drawRect(rectFF, paint);
    }
}
