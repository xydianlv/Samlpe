package com.example.wyyu.gitsamlpe.test.bezier.wave;

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

public class WaveViewAnim extends View {

    public WaveViewAnim(Context context) {
        super(context);
        initWaveView();
    }

    public WaveViewAnim(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWaveView();
    }

    public WaveViewAnim(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWaveView();
    }

    private static final float WAVE_HEIGHT = 28.0f;

    private Paint paintT;
    private Path pathT;
    private Paint paintB;
    private Path pathB;
    private RectF rectF;

    private float viewHeight;
    private float viewWidth;
    private float waveLength;
    private float offset;
    private float waveY;

    private boolean hasInitAnim;

    private void initWaveView() {
        paintT = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintB = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintT.setColor(0x80FF3636);
        paintB.setColor(0x4DFF3636);
        paintT.setStyle(Paint.Style.FILL_AND_STROKE);
        paintB.setStyle(Paint.Style.FILL_AND_STROKE);
        pathT = new Path();
        pathB = new Path();

        hasInitAnim = false;
        waveLength = 0;
        offset = 0;
    }

    private void initAnim() {
        if (hasInitAnim || waveLength <= 0) {
            return;
        }
        hasInitAnim = true;

        ValueAnimator animator = ValueAnimator.ofInt(0, (int) waveLength * 2);
        animator.setDuration(1200);
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
        waveLength = viewWidth / 2;
        waveY = viewHeight / 2;

        initAnim();
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        pathB.reset();
        pathB.moveTo(-offset, waveY);
        for (int index = 0; index < 2; index++) {
            pathB.quadTo(waveLength / 2 - offset + (waveLength * 2 * index), waveY - WAVE_HEIGHT,
                waveLength - offset + (waveLength * 2 * index), waveY);
            pathB.quadTo(waveLength * 3 / 2 - offset + (waveLength * 2 * index),
                waveY + WAVE_HEIGHT, waveLength * 2 - offset + (waveLength * 2 * index), waveY);
        }
        pathB.addArc(rectF, 0, 180);
        pathB.close();
        canvas.drawPath(pathB, paintB);

        pathT.reset();
        pathT.moveTo(-waveLength * 2 + offset, waveY);
        for (int index = 1; index >= 0; index--) {
            pathT.quadTo(waveLength / 2 + offset + (-waveLength * 2 * index), waveY - WAVE_HEIGHT,
                waveLength + offset + (-waveLength * 2 * index), waveY);
            pathT.quadTo(waveLength * 3 / 2 + offset + (-waveLength * 2 * index),
                waveY + WAVE_HEIGHT, waveLength * 2 + offset + (-waveLength * 2 * index), waveY);
        }
        pathT.addArc(rectF, 0, 180);
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
