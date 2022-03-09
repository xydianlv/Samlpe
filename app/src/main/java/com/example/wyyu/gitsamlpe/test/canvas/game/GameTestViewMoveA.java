package com.example.wyyu.gitsamlpe.test.canvas.game;

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

import androidx.annotation.Nullable;

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

    // 辅助展示在外侧的位置数组
    private RectF[] viewFunNext;
    private RectF[] viewFunPre;
    // 辅助展示在外侧的图片数组
    private Rect[] imageFunNext;
    private Rect[] imageFunPre;

    // 滑动监听
    private GestureDetector gesture;
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
    // 最后一次滑动的方向
    private float lastDistance;

    // 单个 Item 展示宽度
    private int viewWidth;
    // 单个 Item 展示高度
    private int viewHeight;

    private void initView() {
        initObject();
        initGesture();
        initData();
    }

    private void initObject() {
        bitmap = BitmapFactory.decodeResource(getResources(), RES_ID);
        paint = new Paint();
    }

    private void initData() {
        imageArray = new Rect[COUNT];
        viewArray = new RectF[COUNT];

        imageFunNext = new Rect[COUNT_FUN];
        imageFunPre = new Rect[COUNT_FUN];
        viewFunNext = new RectF[COUNT_FUN];
        viewFunPre = new RectF[COUNT_FUN];

        for (int index = 0; index < COUNT; index++) {
            viewArray[index] = new RectF();
            imageArray[index] = new Rect();
        }
        for (int index = 0; index < COUNT_FUN; index++) {
            viewFunPre[index] = new RectF();
            viewFunNext[index] = new RectF();
            imageFunPre[index] = new Rect();
            imageFunNext[index] = new Rect();
        }
    }

    private void initGesture() {
        gesture = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
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

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (onScroll == 1) {
                    onScrollEndX();
                }
                if (onScroll == -1) {
                    onScrollEndY();
                }
                onScroll = 0;
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

                imageFunNext[index].left = imageArray[indexPre + index].left;
                imageFunNext[index].right = imageArray[indexPre + index].right;
                imageFunNext[index].top = imageArray[indexPre + index].top;
                imageFunNext[index].bottom = imageArray[indexPre + index].bottom;

                viewFunPre[index].left = (DIVIDE + viewWidth) * (COUNT_FUN - index) * (-1);
                viewFunPre[index].right = viewFunPre[index].left + viewWidth;
                viewFunPre[index].top = viewArray[indexPre].top;
                viewFunPre[index].bottom = viewArray[indexPre].bottom;

                imageFunPre[index].left = imageArray[indexPre + index].left;
                imageFunPre[index].right = imageArray[indexPre + index].right;
                imageFunPre[index].top = imageArray[indexPre + index].top;
                imageFunPre[index].bottom = imageArray[indexPre + index].bottom;
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

                imageFunNext[index].left = imageArray[indexPre + index * ROW].left;
                imageFunNext[index].right = imageArray[indexPre + index * ROW].right;
                imageFunNext[index].top = imageArray[indexPre + index * ROW].top;
                imageFunNext[index].bottom = imageArray[indexPre + index * ROW].bottom;

                viewFunPre[index].left = viewArray[indexPre].left;
                viewFunPre[index].right = viewArray[indexPre].right;
                viewFunPre[index].top = (DIVIDE + viewHeight) * (COUNT_FUN - index) * (-1);
                viewFunPre[index].bottom = viewFunPre[index].top + viewHeight;

                imageFunPre[index].left = imageArray[indexPre + index * ROW].left;
                imageFunPre[index].right = imageArray[indexPre + index * ROW].right;
                imageFunPre[index].top = imageArray[indexPre + index * ROW].top;
                imageFunPre[index].bottom = imageArray[indexPre + index * ROW].bottom;
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
        lastDistance = distanceX;
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
        lastDistance = distanceY;
    }

    private void onScrollEndX() {
        if (lastDistance >= 0) {
            int lastIndex = checkNextItemIndex();
            int indexPre = (scrollIndex / ROW) * ROW;
            int indexNext = indexPre + 2;
        } else {
            int firstIndex = checkPreItemIndex();
        }
    }

    private void onScrollEndY() {

    }

    private int checkNextItemIndex() {
        if (onScroll == 1) {
            int indexPre = (scrollIndex / ROW) * ROW;
            int indexNext = indexPre + 2;
            int maxWidth = viewWidth * COUNT_FUN + DIVIDE * 2;
            while (indexNext >= indexPre) {
                if (viewArray[indexNext].left < maxWidth && viewArray[indexNext].right > maxWidth) {
                    return indexNext;
                }
                indexNext = indexNext - 1;
            }
        }
        if (onScroll == -1) {
            int indexPre = scrollIndex % LIST;
            int indexNext = indexPre + 2 * ROW;
            int maxHeight = viewHeight * COUNT_FUN + DIVIDE * 2;
            while (indexNext >= indexPre) {
                if (viewArray[indexNext].top < maxHeight && viewArray[indexNext].bottom > maxHeight) {
                    return indexNext;
                }
                indexNext = indexNext - 1;
            }
        }
        return 0;
    }

    private int checkPreItemIndex() {
        if (onScroll == 1) {
            int indexPre = (scrollIndex / ROW) * ROW;
            int indexNext = indexPre + 2;
            while (indexPre <= indexNext) {
                if (viewArray[indexPre].left < 0 && viewArray[indexPre].right > 0) {
                    return indexNext;
                }
                indexPre = indexPre + 1;
            }
        }
        if (onScroll == -1) {
            int indexPre = scrollIndex % LIST;
            int indexNext = indexPre + 2 * ROW;
            while (indexPre <= indexNext) {
                if (viewArray[indexPre].top < 0 && viewArray[indexPre].bottom > 0) {
                    return indexNext;
                }
                indexPre = indexPre + 1;
            }
        }
        return 0;
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
            canvas.drawBitmap(bitmap, imageArray[index], viewArray[index], paint);
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
        }
        gesture.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
