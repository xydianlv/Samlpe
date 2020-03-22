package com.example.wyyu.gitsamlpe.test.animation.click;

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
        this.top = top - img.getHeight() * 1.0f / 2;

        this.matrix = new Matrix();
        this.paint = new Paint();
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
        int divideWidth = DIVIDE_ICON_VALUE * index * funValue;
        int divideHeight = DIVIDE_ICON_VALUE * index + DIVIDE_ICON_HEIGHT;

        pointS = new Point((int) left, (int) top);
        pointE = new Point(pointS.x + funValue * DIVIDE_ICON_DATA * index, pointS.y - divideHeight);
        pointM = new Point(pointS.x + divideWidth, pointS.y - divideHeight / 2);
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
        if (t <= 0.2) {
            paint.setAlpha((int) (FULL_ALPHA * t * 4));
            matrix.setTranslate(left, top);
            matrix.preScale(1.0f + (0.2f - t) * 5, 1.0f + (0.2f - t) * 5, img.getWidth() * 1.0f / 2,
                img.getHeight() * 1.0f / 2);
        } else if (t >= 0.8) {
            paint.setAlpha((int) (FULL_ALPHA * m * 2));
            matrix.setTranslate(left, top);
            matrix.preScale(1.0f + (0.2f - m) * 8, 1.0f + (0.2f - m) * 8, img.getWidth() * 1.0f / 2,
                img.getHeight() * 1.0f / 2);
        } else {
            paint.setAlpha(FULL_ALPHA);
            matrix.setTranslate(left, top);
            matrix.preScale(1.0f, 1.0f);
        }
        if (rotate != 0) {
            matrix.preRotate(rotate);
        }
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
        if (m <= 0.6) {
            matrix.preScale(0.6f, 0.6f);
            paint.setAlpha((int) (FULL_ALPHA * m));
        } else {
            matrix.preScale(m, m);
            paint.setAlpha(FULL_ALPHA);
        }
    }
}
