package com.example.wyyu.gitsamlpe.test.bezier.click;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.NonNull;
import com.example.wyyu.gitsamlpe.util.UIUtils;
import java.util.Random;

/**
 * Created by wyyu on 2020-03-22.
 **/

class ClickValue {

    // 画笔
    Paint paint;
    // 位图
    Bitmap img;
    // 位图控制信息
    Matrix matrix;

    // 左边距
    private float left;
    // 顶边距
    private float top;
    // 起始点
    private Point pointS;
    // 终止点
    private Point pointE;
    // 控制点
    private Point pointM;
    // 旋转角度
    private int rotate;

    private static final int DIVIDE_ICON_HEIGHT = UIUtils.dpToPx(120.0f);
    private static final int DIVIDE_ICON_VALUE = UIUtils.dpToPx(32.0f);
    private static final int DIVIDE_ICON_DATA = UIUtils.dpToPx(10.0f);

    private static final int FULL_ALPHA = 255;

    ClickValue(@NonNull Context context, int resId, float left, float top, int index) {
        this.img = BitmapFactory.decodeResource(context.getResources(), resId);
        this.left = left - img.getWidth() * 1.0f / 2;
        this.top = top - img.getHeight() * 1.0f / 2 - DIVIDE_ICON_VALUE;

        this.matrix = new Matrix();
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.paint.setAlpha(FULL_ALPHA);

        int randomInt = new Random().nextInt(3);
        switch (randomInt % 3) {
            case 0:
                rotate = -15;
                break;
            case 1:
                rotate = 0;
                break;
            case 2:
                rotate = 15;
                break;
        }

        if (index == 0) {
            initMainValue();
        } else {
            initIconValue(index);
        }
    }

    private void initMainValue() {
        pointS = null;
        pointM = null;
        pointE = null;
    }

    private void initIconValue(int index) {
        int funValue = index % 2 == 0 ? 1 : -1;
        int random = new Random().nextInt(DIVIDE_ICON_VALUE);
        int divideWidth = (DIVIDE_ICON_VALUE + random) * index * funValue;
        int divideHeight = DIVIDE_ICON_VALUE * index + DIVIDE_ICON_HEIGHT + random;

        pointS = new Point((int) left + (random - DIVIDE_ICON_DATA) * funValue,
            (int) top + UIUtils.dpToPx(12.0f));
        pointE = new Point((int) left + funValue * DIVIDE_ICON_DATA, pointS.y - divideHeight);
        pointM = new Point((int) left + divideWidth, pointS.y - divideHeight / 2);
    }

    /**
     * 刷新当前动画进度
     *
     * @param t 当前动画已进行时长的百分比
     */
    void onProgress(float t) {
        float m = 1.0f - t;

        if (pointS == null || pointE == null || pointM == null) {
            onMainProgress(t, m);
        } else {
            onIconProgress(t, m);
        }
    }

    /**
     * 刷新主图标动画进度
     *
     * @param t 当前动画已进行时长的百分比
     * @param m 当前动画剩余时长百分比
     */
    private void onMainProgress(float t, float m) {
        if (matrix == null) {
            return;
        }
        if (t <= 0.1f) {
            int alpha = (int) (FULL_ALPHA * t * 20);
            paint.setAlpha(alpha < FULL_ALPHA ? alpha : FULL_ALPHA);
            matrix.setTranslate(left, top);
            matrix.preScale(1.0f + (0.1f - t) * 10, 1.0f + (0.1f - t) * 10,
                img.getWidth() * 1.0f / 2, img.getHeight() * 1.0f / 2);
        } else if (t <= 0.2f) {
            paint.setAlpha(FULL_ALPHA);
            matrix.setTranslate(left, top);
            matrix.preScale(0.5f + (t - 0.1f) * 5, 0.5f + (t - 0.1f) * 5, img.getWidth() * 1.0f / 2,
                img.getHeight() * 1.0f / 2);
        } else if (t >= 0.7) {
            paint.setAlpha((int) (FULL_ALPHA * m * 3.34));
            matrix.setTranslate(left, top);
            matrix.preScale(1.0f + (0.3f - m) * 5, 1.0f + (0.3f - m) * 5, img.getWidth() * 1.0f / 2,
                img.getHeight() * 1.0f / 2);
        } else {
            paint.setAlpha(FULL_ALPHA);
            matrix.setTranslate(left, top);
            matrix.preScale(1.0f, 1.0f);
        }
        matrix.preRotate(rotate);
    }

    /**
     * 刷新浮动图标动画进度
     *
     * @param t 当前动画已进行时长的百分比
     * @param m 当前动画剩余时长百分比
     */
    private void onIconProgress(float t, float m) {
        left = m * m * pointS.x + 2 * t * m * pointM.x + t * t * pointE.x;
        top = m * m * pointS.y + 2 * t * m * pointM.y + t * t * pointE.y;

        matrix.setTranslate(left, top);
        if (m <= 0.4) {
            float scale = m + 0.1f < 0.4f ? 0.4f : m + 0.1f;
            matrix.preScale(scale, scale);
            paint.setAlpha((int) (FULL_ALPHA * m * 2.5));
        } else if (m <= 0.9f) {
            matrix.preScale(m + 0.1f, m + 0.1f);
            paint.setAlpha(FULL_ALPHA);
        } else {
            matrix.preScale(1.0f, 1.0f);
            paint.setAlpha(0);
        }
    }
}
