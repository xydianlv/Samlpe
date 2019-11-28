package com.example.wyyu.gitsamlpe.test.lottie;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by wyyu on 2019-11-27.
 **/
public class WaveViewTest extends View {

    public WaveViewTest(Context context) {
        super(context);
        initWaveView();
    }

    public WaveViewTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWaveView();
    }

    public WaveViewTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWaveView();
    }

    private static final float WAVE_LENGTH = 200.0f;
    private static final float WAVE_HEIGHT = 20.0f;

    private Paint paintT;
    private Path pathT;
    private Paint paintB;
    private Path pathB;
    private RectF rectF;

    private float squareSize;
    private float offset;
    private float waveY;
    private float waveX;

    private double sweepAngle;
    private double startAngle;

    private void initWaveView() {
        paintT = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintB = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintT.setColor(0x80FF3636);
        paintB.setColor(0x4DFF3636);
        paintT.setStyle(Paint.Style.FILL_AND_STROKE);
        paintB.setStyle(Paint.Style.FILL_AND_STROKE);
        pathT = new Path();
        pathB = new Path();

        sweepAngle = 180;
        startAngle = 0;
        offset = 0;

        ValueAnimator animator = ValueAnimator.ofInt(0, (int) WAVE_LENGTH * 4);
        animator.setDuration(2800);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> {
            //offset = (int) animation.getAnimatedValue();
            postInvalidate();
        });
        animator.start();
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        squareSize = MeasureSpec.getSize(widthMeasureSpec);

        if (rectF == null) {
            rectF = new RectF(0, 0, squareSize, squareSize);
        }
        waveY = squareSize / 2;
        waveX = 0;
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        pathB.reset();
        pathB.moveTo(waveX - offset, waveY);
        for (int index = 0; index < 4; index++) {
            pathB.quadTo(WAVE_LENGTH / 2 - offset + (WAVE_LENGTH * 2 * index) + waveX,
                waveY - WAVE_HEIGHT, WAVE_LENGTH - offset + (WAVE_LENGTH * 2 * index) - waveX,
                waveY);
            pathB.quadTo(WAVE_LENGTH * 3 / 2 - offset + (WAVE_LENGTH * 2 * index) + waveX,
                waveY + WAVE_HEIGHT, WAVE_LENGTH * 2 - offset + (WAVE_LENGTH * 2 * index) - waveX,
                waveY);
        }
        pathB.addArc(rectF, (float) startAngle, (float) sweepAngle);
        pathB.close();
        canvas.drawPath(pathB, paintB);

        pathT.reset();
        pathT.moveTo(waveX - WAVE_LENGTH * 6 + offset, waveY);
        for (int index = 3; index >= 0; index--) {
            pathT.quadTo(WAVE_LENGTH / 2 + offset + (-WAVE_LENGTH * 2 * index) + waveX,
                waveY - WAVE_HEIGHT, WAVE_LENGTH + offset + (-WAVE_LENGTH * 2 * index) - waveX,
                waveY);
            pathT.quadTo(WAVE_LENGTH * 3 / 2 + offset + (-WAVE_LENGTH * 2 * index) + waveX,
                waveY + WAVE_HEIGHT, WAVE_LENGTH * 2 + offset + (-WAVE_LENGTH * 2 * index) - waveX,
                waveY);
        }

        pathT.addArc(rectF, (float) startAngle, (float) sweepAngle);
        pathT.close();
        canvas.drawPath(pathT, paintT);
    }

    /**
     * 刷新当前充满整个圆形的百分比
     *
     * @param progress 百分数，取值 [0-100]
     */
    public void refreshProgress(int progress) {
        if (squareSize <= 0) {
            return;
        }
        waveY = squareSize - (progress * 1.0f / 100) * squareSize;

        float half = squareSize / 2;
        float funValue = waveY >= half ? waveY - half : half - waveY;

        double funX = Math.sqrt(half * half - funValue * funValue);
        waveX = (float) (half - funX);

        double r = Math.asin(funX / half) / 3.1415926f * 180;

        sweepAngle = waveY >= half ? r * 2 : 360 - r * 2;
        startAngle = waveY >= half ? 90 - r : r - 90;

        Log.e("WaveViewTest", "squareSize : "
            + squareSize
            + "  waveY : "
            + waveY
            + "  half : "
            + half
            + "  funX : "
            + funX
            + "  waveX : "
            + waveX
            + "  r : "
            + r
            + "  sweepAngle : "
            + sweepAngle
            + "  startAngle : "
            + startAngle);
    }
}