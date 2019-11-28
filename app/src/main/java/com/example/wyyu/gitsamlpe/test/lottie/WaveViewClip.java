package com.example.wyyu.gitsamlpe.test.lottie;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by wyyu on 2019-11-27.
 **/

public class WaveViewClip extends View {

    public WaveViewClip(Context context) {
        super(context);
        initWaveView();
    }

    public WaveViewClip(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWaveView();
    }

    public WaveViewClip(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWaveView();
    }

    private static final float WAVE_LENGTH = 200.0f;
    private static final float WAVE_HEIGHT = 20.0f;

    private Paint paintT;
    private Path pathT;
    private Paint paintB;
    private Path pathB;

    private Path pathCircle;
    private RectF rectF;

    private float viewHeight;
    private float viewWidth;
    private float offset;
    private float waveY;

    private void initWaveView() {
        paintT = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintB = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintT.setColor(0x80FF3636);
        paintB.setColor(0x4DFF3636);
        paintT.setStyle(Paint.Style.FILL_AND_STROKE);
        paintB.setStyle(Paint.Style.FILL_AND_STROKE);
        paintT.setAntiAlias(true);
        paintB.setAntiAlias(true);
        pathT = new Path();
        pathB = new Path();

        pathCircle = new Path();
        offset = 0;

        ValueAnimator animator = ValueAnimator.ofInt(0, (int) WAVE_LENGTH * 4);
        animator.setDuration(2800);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> {
            offset = (int) animation.getAnimatedValue();
            postInvalidate();
        });
        animator.start();
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);

        if (rectF == null) {
            rectF = new RectF(0, 0, viewWidth, viewHeight);
        }
        waveY = viewHeight * 2 / 3;
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        pathCircle.addRoundRect(rectF, viewWidth / 2, viewHeight / 2, Path.Direction.CW);
        canvas.clipPath(pathCircle);

        pathB.reset();
        pathB.moveTo(0 - offset, waveY);
        for (int index = 0; index < 4; index++) {
            pathB.quadTo(WAVE_LENGTH / 2 - offset + (WAVE_LENGTH * 2 * index), waveY - WAVE_HEIGHT,
                WAVE_LENGTH - offset + (WAVE_LENGTH * 2 * index), waveY);
            pathB.quadTo(WAVE_LENGTH * 3 / 2 - offset + (WAVE_LENGTH * 2 * index),
                waveY + WAVE_HEIGHT, WAVE_LENGTH * 2 - offset + (WAVE_LENGTH * 2 * index), waveY);
        }
        pathB.lineTo(viewWidth, viewHeight);
        pathB.lineTo(0, viewHeight);
        pathB.close();
        canvas.drawPath(pathB, paintB);

        pathT.reset();
        pathT.moveTo(0 - WAVE_LENGTH * 6 + offset, waveY);
        for (int index = 3; index >= 0; index--) {
            pathT.quadTo(WAVE_LENGTH / 2 + offset + (-WAVE_LENGTH * 2 * index), waveY - WAVE_HEIGHT,
                WAVE_LENGTH + offset + (-WAVE_LENGTH * 2 * index), waveY);
            pathT.quadTo(WAVE_LENGTH * 3 / 2 + offset + (-WAVE_LENGTH * 2 * index),
                waveY + WAVE_HEIGHT, WAVE_LENGTH * 2 + offset + (-WAVE_LENGTH * 2 * index), waveY);
        }
        pathT.lineTo(viewWidth, viewHeight);
        pathT.lineTo(0, viewHeight);
        pathT.close();
        canvas.drawPath(pathT, paintT);
    }

    /**
     * 刷新当前充满整个圆形的百分比
     *
     * @param progress 百分数，取值 [0-100]
     */
    public void refreshProgress(int progress) {
        if (viewHeight <= 0 || viewWidth <= 0) {
            return;
        }
        waveY = (progress * 1.0f / 100) * viewHeight;
    }
}