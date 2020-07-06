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
    private static final int SCREEN_DIVIDE = SCREEN_WIDTH / 6;
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

        pointM = new Point((pointS.x - pointE.x) / 3 + pointE.x,
            Math.min(pointS.y, pointE.y) - SCREEN_WIDTH / 3);
    }

    private void initRight(int index) {
        pointS = new Point((int) left, (int) top);
        int data = SCREEN_DIVIDE * (index % 6 - 1) + new Random().nextInt(SCREEN_DIVIDE);
        int side = index % 4 == 1 ? -1 : 1;

        // X轴的随机间距
        int x = pointS.x - data + (SIZE_IMAGE * 2 * side);
        // 动画的随机半径大小
        int r = SCREEN_WIDTH * 3 / 4 + new Random().nextInt(SCREEN_WIDTH / 4);
        // y轴的随机间距
        int y = pointS.y - (int) Math.sqrt(r * r - data * data) * side + (side < 0 ? 0
            : (SCREEN_DIVIDE * 2 - data / 2));

        pointE = new Point(x, y - (side < 0 ? SCREEN_WIDTH / 2 : 0));
    }

    private void initLeft(int index) {
        pointS = new Point((int) left, (int) top);

        int data = SCREEN_DIVIDE * (index % 6 - 1) + new Random().nextInt(SCREEN_DIVIDE);
        int side = index % 4 == 1 ? -1 : 1;

        // X轴的随机间距
        int x = pointS.x + data - (SIZE_IMAGE * 2 * side);
        // 动画的随机半径大小
        int r = SCREEN_WIDTH * 3 / 4 + new Random().nextInt(SCREEN_WIDTH / 4);
        // y轴的随机间距
        int y = pointS.y - (int) Math.sqrt(r * r - data * data) * side + (side < 0 ? 0
            : (SCREEN_DIVIDE * 2 - data / 2));

        pointE = new Point(x, y - (side < 0 ? SCREEN_WIDTH / 2 : 0));
    }

    private void initMid(int index) {
        pointS = new Point((int) left + SIZE_IMAGE / 2, (int) top);

        //int data = SCREEN_DIVIDE * (index % 6 - 1) + new Random().nextInt(SCREEN_DIVIDE);
        //
        //// 动画的随机半径大小
        //int r = SCREEN_WIDTH * 3 / 4 + new Random().nextInt(SCREEN_WIDTH / 4);
        //// y轴的随机间距
        //int y = pointS.y - (int) Math.sqrt(r * r - (pointS.x - data) * (pointS.x - data));
        //
        //pointE = new Point(data, y);

        int data = new Random().nextInt(SCREEN_WIDTH);
        pointE = new Point(pointS.x + (data * (index % 2 == 0 ? 1 : -1)),
            pointS.y - (SCREEN_WIDTH - data));
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

        if (m < 0.5) {
            matrix.preScale((0.6f + 0.8f * m) * scaleX, (0.6f + 0.8f * m) * scaleY);
        } else {
            matrix.preScale(1.0f * scaleX, 1.0f * scaleY);
        }

        if (m <= 0.25) {
            paint.setAlpha((int) (FULL_ALPHA * m * 4.0f));
        } else if (m <= 0.9f) {
            paint.setAlpha(FULL_ALPHA);
        } else {
            paint.setAlpha((int) (FULL_ALPHA * t * 10.0f));
        }
    }
}
