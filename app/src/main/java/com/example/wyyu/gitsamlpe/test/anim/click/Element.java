package com.example.wyyu.gitsamlpe.test.anim.click;

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
 * Created by wyyu on 2020-06-17.
 **/

class Element {

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

    // 宽度的缩放比
    private float scaleX;
    // 高度的缩放比
    private float scaleY;

    private static final int SCREEN_WIDTH = UIUtils.getScreenWidth();
    private static final int SIZE_IMAGE = UIUtils.dpToPx(32.0f);

    private static final int FULL_ALPHA = 255;

    Element(@NonNull Context context, int resId, float left, float top, int index) {
        this.img = BitmapFactory.decodeResource(context.getResources(), resId);
        this.left = left;
        this.top = top;

        this.matrix = new Matrix();
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.paint.setAlpha(FULL_ALPHA);

        this.scaleX = this.img == null ? 1.0f : SIZE_IMAGE * 1.0f / this.img.getWidth();
        this.scaleY = this.img == null ? 1.0f : SIZE_IMAGE * 1.0f / this.img.getHeight();

        initIconValue(index);
    }

    private void initIconValue(int index) {
        if (left >= UIUtils.getScreenWidth() * 2 / 3) {
            initRight(index);
        } else if (left <= UIUtils.getScreenWidth() / 3) {
            initLeft(index);
        } else {
            initMid(index);
        }
    }

    private void initRight(int index) {
        pointS = new Point((int) left + SIZE_IMAGE, (int) top + SIZE_IMAGE);
        int data = new Random().nextInt(SCREEN_WIDTH);
        int side = index % 5 == 1 ? -1 : 1;

        int x = pointS.x - data + (SIZE_IMAGE * 4 * side);
        int y = pointS.y - (int) Math.sqrt(SCREEN_WIDTH * SCREEN_WIDTH - data * data) * side;

        pointE = new Point(x, y - (side < 0 ? SCREEN_WIDTH / 2 : 0));

        pointM = null;
    }

    private void initLeft(int index) {
        pointS = new Point((int) left, (int) top + SIZE_IMAGE);

        int data = new Random().nextInt(SCREEN_WIDTH);
        pointE = new Point(pointS.x + data - SIZE_IMAGE * 2,
            pointS.y - (SCREEN_WIDTH - data) * (index % 4 == 1 ? -1 : 1));

        pointM = null;
    }

    private void initMid(int index) {

        pointS = new Point((int) left + SIZE_IMAGE / 2, (int) top + SIZE_IMAGE);

        int data = new Random().nextInt(SCREEN_WIDTH);
        pointE = new Point(pointS.x + (data * (index % 2 == 0 ? 1 : -1)),
            pointS.y - (SCREEN_WIDTH - data));

        pointM = new Point((pointS.x - pointE.x) / 3 + pointE.x,
            Math.min(pointS.y, pointE.y) - SCREEN_WIDTH / 5);
    }

    /**
     * 刷新当前动画进度
     *
     * @param t 当前动画已进行时长的百分比
     */
    void onProgress(float t) {
        float m = 1.0f - t;

        if (pointM != null) {
            left = m * m * pointS.x + 2 * t * m * pointM.x + t * t * pointE.x;
            top = m * m * pointS.y + 2 * t * m * pointM.y + t * t * pointE.y;
        } else {
            left = (pointE.x - pointS.x) * t + pointS.x;
            top = (pointE.y - pointS.y) * t + pointS.y;
        }

        matrix.setTranslate(left, top);

        if (m <= 0.2) {
            matrix.preScale(1.4f * scaleX, 1.4f * scaleY);
            paint.setAlpha((int) (FULL_ALPHA * m * 5.0f));
        } else if (m <= 0.9f) {
            matrix.preScale((0.9f - m + 0.7f) * scaleX, (0.9f - m + 0.7f) * scaleY);
            paint.setAlpha(FULL_ALPHA);
        } else {
            matrix.preScale(0.7f * scaleX, 0.7f * scaleY);
            paint.setAlpha(0);
        }
    }
}
