package com.example.wyyu.gitsamlpe.test.canvas.game;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.example.wyyu.gitsamlpe.R;

public class GameTestViewMoveA extends View {

    public GameTestViewMoveA(Context context) {
        super(context);
        initView();
    }

    public GameTestViewMoveA(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GameTestViewMoveA(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    // 图片
    private static final int RES_ID = R.mipmap.image_test_1;

    // 动画时长
    private static final int ANIM_TIME = 120;
    // item 间距
    private static final int DIVIDE = 12;
    // 列数
    private static final int LIST = 3;
    // 行数
    private static final int ROW = 3;
    // 总数
    private static final int COUNT = LIST * ROW;
    // 辅助展示的 item 个数
    private static final int COUNT_FUN = 3;

    // Item 在控件中的位置数组
    private RectF[] viewArray;
    // Item 所展示的图片位置
    private Rect[] imageArray;
    // 判断数组顺序的 Array
    private int[] indexArray;

    // 辅助展示在外侧的位置数组
    private RectF[] viewFunNext;
    private RectF[] viewFunPre;
    // 执行动画的位置数组
    private RectF[] viewArrayAnim;
    private RectF[] viewFunAnim;
    // 辅助展示在外侧的图片数组
    private Rect[] imageFunNext;
    private Rect[] imageFunPre;

    // 滑动监听
    private GestureDetector gesture;
    // 松手后的X轴动画
    private ValueAnimator animatorX;
    // 松手后的Y轴动画
    private ValueAnimator animatorY;
    // 待绘制的图片数据
    private Bitmap bitmap;
    // 画笔
    private Paint paint;

    // 滑动的行/列
    private int scrollIndex;
    // 单次滑动的总滑动距离
    private float scrollAll;
    // 滑动的方向，0-未滑动，1-横向，-1-竖向
    private int onScroll;
    // 当前点击的 Index
    private int divideIndex;

    // 单个 Item 展示宽度
    private int viewWidth;
    // 单个 Item 展示高度
    private int viewHeight;

    private void initView() {
        initObject();
        initGesture();
        initAnim();
        initData();
    }

    private void initObject() {
        bitmap = BitmapFactory.decodeResource(getResources(), RES_ID);
        paint = new Paint();
    }

    private void initData() {
        imageArray = new Rect[COUNT];
        viewArray = new RectF[COUNT];
        indexArray = new int[COUNT];

        imageFunNext = new Rect[COUNT_FUN];
        imageFunPre = new Rect[COUNT_FUN];
        viewFunNext = new RectF[COUNT_FUN];
        viewFunPre = new RectF[COUNT_FUN];
        viewFunAnim = new RectF[COUNT_FUN];
        viewArrayAnim = new RectF[COUNT_FUN];

        for (int index = 0; index < COUNT; index++) {
            viewArray[index] = new RectF();
            imageArray[index] = new Rect();
            indexArray[index] = index;
        }
        for (int index = 0; index < COUNT_FUN; index++) {
            viewFunPre[index] = new RectF();
            viewFunNext[index] = new RectF();
            viewFunAnim[index] = new RectF();
            viewArrayAnim[index] = new RectF();
            imageFunPre[index] = new Rect();
            imageFunNext[index] = new Rect();
        }
    }

    private void initAnim() {
        animatorX = new ValueAnimator();
        animatorX.setInterpolator(new LinearInterpolator());
        animatorX.setDuration(ANIM_TIME);
        animatorX.addUpdateListener(valueAnimator -> {
            if (animatorX == null) {
                return;
            }
            float animValue = (float) animatorX.getAnimatedValue();

            int indexPre = (scrollIndex / ROW) * ROW;
            for (int index = 0; index < COUNT_FUN; index++) {
                viewArray[index + indexPre].left = viewArrayAnim[index].left - animValue;
                viewArray[index + indexPre].right = viewArrayAnim[index].right - animValue;
                if (scrollAll > 0) {
                    viewFunNext[index].left = viewFunAnim[index].left - animValue;
                    viewFunNext[index].right = viewFunAnim[index].right - animValue;
                } else {
                    viewFunPre[index].left = viewFunAnim[index].left - animValue;
                    viewFunPre[index].right = viewFunAnim[index].right - animValue;
                }
            }
            invalidate();
        });
        animatorX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                turnArrayX();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                turnArrayX();
            }
        });

        animatorY = new ValueAnimator();
        animatorY.setInterpolator(new FastOutSlowInInterpolator());
        animatorY.setDuration(ANIM_TIME);
        animatorY.addUpdateListener(valueAnimator -> {
            if (animatorY == null) {
                return;
            }
            float animValue = (float) animatorY.getAnimatedValue();

            int indexPre = scrollIndex % LIST;
            for (int index = 0; index < COUNT_FUN; index++) {
                viewArray[index * ROW + indexPre].top = viewArrayAnim[index].top - animValue;
                viewArray[index * ROW + indexPre].bottom = viewArrayAnim[index].bottom - animValue;
                if (scrollAll > 0) {
                    viewFunNext[index].top = viewFunAnim[index].top - animValue;
                    viewFunNext[index].bottom = viewFunAnim[index].bottom - animValue;
                } else {
                    viewFunPre[index].top = viewFunAnim[index].top - animValue;
                    viewFunPre[index].bottom = viewFunAnim[index].bottom - animValue;
                }
            }
            invalidate();
        });
        animatorY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                turnArrayY();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                turnArrayY();
            }
        });
    }

    private void initGesture() {
        gesture = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                if (animatorX != null) {
                    animatorX.cancel();
                }
                if (animatorY != null) {
                    animatorY.cancel();
                }
                initTouch(e);
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (onScroll == 0) {
                    startScroll(distanceX, distanceY);
                }
                if (onScroll == 1) {
                    onScrollX(distanceX);
                }
                if (onScroll == -1) {
                    onScrollY(distanceY);
                }
                invalidate();
                return true;
            }
        });
    }

    private void initTouch(MotionEvent e) {
        float touchX = e.getX();
        float touchY = e.getY();

        int indexX = (int) (touchX / viewWidth);
        int indexY = (int) (touchY / viewHeight);

        scrollIndex = indexY * ROW + indexX;
        scrollAll = 0.0f;
        onScroll = 0;
    }

    private void startScroll(float distanceX, float distanceY) {
        if (Math.abs(distanceX) > Math.abs(distanceY)) {
            onScroll = 1;
        } else {
            onScroll = -1;
        }
        if (onScroll == 1) {
            int indexPre = (scrollIndex / ROW) * ROW;
            int indexNext = indexPre + 2;
            for (int index = 0; index < COUNT_FUN; index++) {
                viewFunNext[index].left = viewArray[indexNext].right + DIVIDE * (index + 1) + viewWidth * index;
                viewFunNext[index].right = viewFunNext[index].left + viewWidth;
                viewFunNext[index].top = viewArray[indexPre].top;
                viewFunNext[index].bottom = viewArray[indexPre].bottom;

                imageFunNext[index].left = imageArray[indexArray[indexPre + index]].left;
                imageFunNext[index].right = imageArray[indexArray[indexPre + index]].right;
                imageFunNext[index].top = imageArray[indexArray[indexPre + index]].top;
                imageFunNext[index].bottom = imageArray[indexArray[indexPre + index]].bottom;

                viewFunPre[index].left = (DIVIDE + viewWidth) * (COUNT_FUN - index) * (-1);
                viewFunPre[index].right = viewFunPre[index].left + viewWidth;
                viewFunPre[index].top = viewArray[indexPre].top;
                viewFunPre[index].bottom = viewArray[indexPre].bottom;

                imageFunPre[index].left = imageArray[indexArray[indexPre + index]].left;
                imageFunPre[index].right = imageArray[indexArray[indexPre + index]].right;
                imageFunPre[index].top = imageArray[indexArray[indexPre + index]].top;
                imageFunPre[index].bottom = imageArray[indexArray[indexPre + index]].bottom;
            }
        }
        if (onScroll == -1) {
            int indexPre = scrollIndex % LIST;
            int indexNext = indexPre + 2 * ROW;
            for (int index = 0; index < COUNT_FUN; index++) {
                viewFunNext[index].left = viewArray[indexNext].left;
                viewFunNext[index].right = viewArray[indexNext].right;
                viewFunNext[index].top = viewArray[indexNext].bottom + DIVIDE * (index + 1) + viewHeight * index;
                viewFunNext[index].bottom = viewFunNext[index].top + viewHeight;

                imageFunNext[index].left = imageArray[indexArray[indexPre + index * ROW]].left;
                imageFunNext[index].right = imageArray[indexArray[indexPre + index * ROW]].right;
                imageFunNext[index].top = imageArray[indexArray[indexPre + index * ROW]].top;
                imageFunNext[index].bottom = imageArray[indexArray[indexPre + index * ROW]].bottom;

                viewFunPre[index].left = viewArray[indexPre].left;
                viewFunPre[index].right = viewArray[indexPre].right;
                viewFunPre[index].top = (DIVIDE + viewHeight) * (COUNT_FUN - index) * (-1);
                viewFunPre[index].bottom = viewFunPre[index].top + viewHeight;

                imageFunPre[index].left = imageArray[indexArray[indexPre + index * ROW]].left;
                imageFunPre[index].right = imageArray[indexArray[indexPre + index * ROW]].right;
                imageFunPre[index].top = imageArray[indexArray[indexPre + index * ROW]].top;
                imageFunPre[index].bottom = imageArray[indexArray[indexPre + index * ROW]].bottom;
            }
        }
    }

    private void onScrollX(float distanceX) {
        int data = scrollIndex / LIST;
        for (int index = 0; index < COUNT; index++) {
            if (index / LIST != data) {
                continue;
            }
            viewArray[index].left = viewArray[index].left - distanceX;
            viewArray[index].right = viewArray[index].right - distanceX;
        }
        for (int index = 0; index < COUNT_FUN; index++) {
            viewFunPre[index].left = viewFunPre[index].left - distanceX;
            viewFunPre[index].right = viewFunPre[index].right - distanceX;
            viewFunNext[index].left = viewFunNext[index].left - distanceX;
            viewFunNext[index].right = viewFunNext[index].right - distanceX;
        }
        scrollAll = scrollAll + distanceX;
    }

    private void onScrollY(float distanceY) {
        int data = scrollIndex % ROW;
        for (int index = 0; index < COUNT; index++) {
            if (index % ROW != data) {
                continue;
            }
            viewArray[index].top = viewArray[index].top - distanceY;
            viewArray[index].bottom = viewArray[index].bottom - distanceY;
        }
        for (int index = 0; index < COUNT_FUN; index++) {
            viewFunPre[index].top = viewFunPre[index].top - distanceY;
            viewFunPre[index].bottom = viewFunPre[index].bottom - distanceY;
            viewFunNext[index].top = viewFunNext[index].top - distanceY;
            viewFunNext[index].bottom = viewFunNext[index].bottom - distanceY;
        }
        scrollAll = scrollAll + distanceY;
    }

    private void onTouchUp() {
        this.divideIndex = scrollAll >= 0 ? checkPreItemIndex() : checkNextItemIndex();
        if (onScroll == 1) {
            onScrollEndX();
        }
        if (onScroll == -1) {
            onScrollEndY();
        }
        onScroll = 0;
    }

    private void clearFun() {
        for (int index = 0; index < COUNT_FUN; index++) {
            imageFunPre[index].left = 0;
            imageFunPre[index].right = 0;
            imageFunNext[index].left = 0;
            imageFunNext[index].right = 0;
            imageFunPre[index].top = 0;
            imageFunPre[index].bottom = 0;
            imageFunNext[index].top = 0;
            imageFunNext[index].bottom = 0;
        }
    }

    private void onScrollEndX() {
        int maxWidth = viewWidth * COUNT_FUN + DIVIDE * 2;

        float leftValue = Math.abs(scrollAll >= 0
                ? viewArray[divideIndex].left
                : maxWidth - viewArray[divideIndex].left);

        float rightValue = Math.abs(scrollAll >= 0
                ? viewArray[divideIndex].right
                : viewArray[divideIndex].right - maxWidth);

        int indexPre = (scrollIndex / ROW) * ROW;
        for (int index = 0; index < COUNT_FUN; index++) {
            viewArrayAnim[index].left = viewArray[index + indexPre].left;
            viewArrayAnim[index].right = viewArray[index + indexPre].right;
            viewFunAnim[index].left = scrollAll > 0 ? viewFunNext[index].left : viewFunPre[index].left;
            viewFunAnim[index].right = scrollAll > 0 ? viewFunNext[index].right : viewFunPre[index].right;
        }
        if (scrollAll > 0) {
            animatorX.setFloatValues(0, rightValue);
        } else {
            animatorX.setFloatValues(0, -leftValue - DIVIDE);
        }
        animatorX.start();
    }

    private void turnArrayX() {
        int indexPre = (scrollIndex / ROW) * ROW;
        int indexNext = indexPre + 2;

        for (int index = 0; index < COUNT_FUN; index++) {
            viewArray[index + indexPre].left = (viewWidth + DIVIDE) * (index % LIST);
            viewArray[index + indexPre].right = viewArray[index + indexPre].left + viewWidth;
        }
        int[] array = new int[COUNT_FUN];
        for (int index = 0; index < COUNT_FUN; index++) {
            int imageIndex = divideIndex + index + (scrollAll > 0 ? 1 : 0);
            if (imageIndex > indexNext) {
                imageIndex = imageIndex - LIST;
            }
            array[index] = indexArray[imageIndex];
        }
        System.arraycopy(array, 0, indexArray, indexPre, COUNT_FUN);

        clearFun();
        invalidate();
    }

    private void onScrollEndY() {
        int maxHeight = viewHeight * COUNT_FUN + DIVIDE * 2;

        float topValue = Math.abs(scrollAll >= 0
                ? viewArray[divideIndex].top
                : maxHeight - viewArray[divideIndex].top);

        float bottomValue = Math.abs(scrollAll >= 0
                ? viewArray[divideIndex].bottom
                : viewArray[divideIndex].bottom - maxHeight);

        int indexPre = scrollIndex % LIST;
        for (int index = 0; index < COUNT_FUN; index++) {
            viewArrayAnim[index].top = viewArray[index * ROW + indexPre].top;
            viewArrayAnim[index].bottom = viewArray[index * ROW + indexPre].bottom;
            viewFunAnim[index].top = scrollAll > 0 ? viewFunNext[index].top : viewFunPre[index].top;
            viewFunAnim[index].bottom = scrollAll > 0 ? viewFunNext[index].bottom : viewFunPre[index].bottom;
        }
        if (scrollAll > 0) {
            animatorY.setFloatValues(0, bottomValue);
        } else {
            animatorY.setFloatValues(0, -topValue - DIVIDE);
        }
        animatorY.start();
    }

    private void turnArrayY() {
        int indexPre = scrollIndex % LIST;
        int indexNext = indexPre + 2 * ROW;

        for (int index = 0; index < COUNT_FUN; index++) {
            viewArray[index * ROW + indexPre].top = (viewHeight + DIVIDE) * (index % ROW);
            viewArray[index * ROW + indexPre].bottom = viewArray[index * ROW + indexPre].top + viewHeight;
        }
        int[] array = new int[COUNT_FUN];
        for (int index = 0; index < COUNT_FUN; index++) {
            int imageIndex = divideIndex + index * ROW + (scrollAll > 0 ? ROW : 0);
            if (imageIndex > indexNext) {
                imageIndex = imageIndex - LIST * ROW;
            }
            array[index] = indexArray[imageIndex];
        }
        for (int index = 0; index < COUNT_FUN; index++) {
            indexArray[index * COUNT_FUN + indexPre] = array[index];
        }
        clearFun();
        invalidate();
    }

    private int checkNextItemIndex() {
        int index = -1;
        if (onScroll == 1) {
            int indexPre = (scrollIndex / ROW) * ROW;
            int indexNext = indexPre + 2;
            int maxWidth = viewWidth * COUNT_FUN + DIVIDE * 2;
            while (indexNext >= indexPre) {
                if (viewArray[indexNext].left < maxWidth && viewArray[indexNext].right > maxWidth) {
                    index = indexNext;
                    break;
                }
                indexNext = indexNext - 1;
            }
            if (index == -1) {
                index = indexPre;
            }
        }
        if (onScroll == -1) {
            int indexPre = scrollIndex % LIST;
            int indexNext = indexPre + 2 * ROW;
            int maxHeight = viewHeight * COUNT_FUN + DIVIDE * 2;
            while (indexNext >= indexPre) {
                if (viewArray[indexNext].top < maxHeight && viewArray[indexNext].bottom > maxHeight) {
                    index = indexNext;
                    break;
                }
                indexNext = indexNext - 1;
            }
            if (index == -1) {
                index = indexPre;
            }
        }
        return index;
    }

    private int checkPreItemIndex() {
        int index = -1;
        if (onScroll == 1) {
            int indexPre = (scrollIndex / ROW) * ROW;
            int indexNext = indexPre + 2;
            while (indexPre <= indexNext) {
                if (viewArray[indexPre].left < 0 && viewArray[indexPre].right > 0) {
                    index = indexPre;
                    break;
                }
                indexPre = indexPre + 1;
            }
            if (index == -1) {
                index = indexNext;
            }
        }
        if (onScroll == -1) {
            int indexPre = scrollIndex % LIST;
            int indexNext = indexPre + 2 * ROW;
            while (indexPre <= indexNext) {
                if (viewArray[indexPre].top < 0 && viewArray[indexPre].bottom > 0) {
                    index = indexPre;
                    break;
                }
                indexPre = indexPre + 1;
            }
            if (index == -1) {
                index = indexNext;
            }
        }
        return index;
    }

    @Override
    public void onMeasure(int valueMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(valueMeasureSpec, valueMeasureSpec);

        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();

        int imgHeight = bitmap.getHeight();
        int imgWidth = bitmap.getWidth();

        float aspectView = viewWidth * 1.0f / viewHeight;
        float aspectImg = imgWidth * 1.0f / imgHeight;

        viewWidth = (viewWidth - DIVIDE * (LIST - 1)) / LIST;
        viewHeight = (viewHeight - DIVIDE * (ROW - 1)) / ROW;

        if (aspectView > aspectImg) {
            imgHeight = ((int) (imgWidth * aspectView) - (DIVIDE * (ROW - 1))) / ROW;
            imgWidth = (imgWidth - (DIVIDE * (LIST - 1))) / LIST;
        } else {
            imgWidth = ((int) (imgHeight * aspectView) - (DIVIDE * (LIST - 1))) / LIST;
            imgHeight = (imgHeight - (DIVIDE * (ROW - 1))) / ROW;
        }

        for (int index = 0; index < COUNT; index++) {

            int data = index / LIST;

            viewArray[index].left = (viewWidth + DIVIDE) * (index % LIST);
            viewArray[index].right = viewArray[index].left + viewWidth;
            viewArray[index].top = (viewHeight + DIVIDE) * data;
            viewArray[index].bottom = viewArray[index].top + viewHeight;

            imageArray[index].left = imgWidth * (index % LIST);
            imageArray[index].right = imageArray[index].left + imgWidth;
            imageArray[index].top = imgHeight * data;
            imageArray[index].bottom = imageArray[index].top + imgHeight;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap == null) {
            return;
        }
        for (int index = 0; index < COUNT; index++) {
            canvas.drawBitmap(bitmap, imageArray[indexArray[index]], viewArray[index], paint);
        }
        for (int index = 0; index < COUNT_FUN; index++) {
            canvas.drawBitmap(bitmap, imageFunPre[index], viewFunPre[index], paint);
            canvas.drawBitmap(bitmap, imageFunNext[index], viewFunNext[index], paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            performClick();
            onTouchUp();
        }
        gesture.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
