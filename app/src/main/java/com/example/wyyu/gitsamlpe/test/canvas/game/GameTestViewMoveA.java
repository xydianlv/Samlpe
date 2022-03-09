package com.example.wyyu.gitsamlpe.test.canvas.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.airbnb.lottie.L;
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

    // Item 在控件中的位置数组
    private SparseArray<RectF> viewArray;
    // Item 所展示的图片位置
    private SparseArray<Rect> imageArray;
    // 辅助展示在外侧的位置数组
    private SparseArray<RectF> viewFun;
    // 辅助展示在外侧的图片数组
    private SparseArray<Rect> imageFun;

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

    // 单个 Item 展示宽度
    private int viewWidth;
    // 单个 Item 展示高度
    private int viewHeight;
    // 单个 Item 展示的图片宽度
    private int imgWidth;
    // 单个 Item 展示的图片高度
    private int imgHeight;

    private void initView() {
        initObject();
        initGesture();
        initData();
    }

    private void initObject() {
        bitmap = BitmapFactory.decodeResource(getResources(), RES_ID);
        paint = new Paint();
    }

    private void initGesture() {
        gesture = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                float touchX = e.getX();
                float touchY = e.getY();

                int indexX = (int) (touchX / viewWidth);
                int indexY = (int) (touchY / viewHeight);

                scrollIndex = indexY * ROW + indexX;
                scrollAll = 0.0f;
                onScroll = 0;
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (onScroll == 0) {
                    if (Math.abs(distanceX) > Math.abs(distanceY)) {
                        onScroll = 1;
                    } else {
                        onScroll = -1;
                    }
                }
                if (onScroll == 1) {
                    int data = scrollIndex / LIST;
                    for (int index = 0; index < COUNT; index++) {
                        if (index / LIST != data) {
                            continue;
                        }
                        RectF rectF = viewArray.get(index);
                        rectF.left = rectF.left - distanceX;
                        rectF.right = rectF.right - distanceX;
                    }
                    scrollAll = scrollAll + distanceX;
                }
                if (onScroll == -1) {
                    int data = scrollIndex % ROW;
                    for (int index = 0; index < COUNT; index++) {
                        if (index % ROW != data) {
                            continue;
                        }
                        RectF rectF = viewArray.get(index);
                        rectF.top = rectF.top - distanceY;
                        rectF.bottom = rectF.bottom - distanceY;
                    }
                    scrollAll = scrollAll + distanceY;
                }
                invalidate();
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                for (int index = 0; index < 2; index++) {
                    RectF rectFPre = viewFun.get(0);
                    rectFPre.left = 0;
                    rectFPre.right = 0;
                    rectFPre.top = 0;
                    rectFPre.bottom = 0;
                    RectF rectFNext = viewFun.get(1);
                    rectFNext.left = 0;
                    rectFNext.right = 0;
                    rectFNext.top = 0;
                    rectFNext.bottom = 0;
                }
                onScroll = 0;
                return true;
            }
        });
    }

    private void initData() {
        imageArray = new SparseArray<>();
        viewArray = new SparseArray<>();
        imageFun = new SparseArray<>();
        viewFun = new SparseArray<>();

        for (int index = 0; index < COUNT; index++) {
            viewArray.put(index, new RectF());
            imageArray.put(index, new Rect());
        }
        for (int index = 0; index < 2; index++) {
            viewFun.put(index, new RectF());
            imageFun.put(index, new Rect());
        }
    }

    @Override
    public void onMeasure(int valueMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(valueMeasureSpec, valueMeasureSpec);

        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();

        imgHeight = bitmap.getHeight();
        imgWidth = bitmap.getWidth();

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

            RectF rectF = viewArray.get(index);
            rectF.left = (viewWidth + DIVIDE) * (index % LIST);
            rectF.right = rectF.left + viewWidth;
            rectF.top = (viewHeight + DIVIDE) * data;
            rectF.bottom = rectF.top + viewHeight;

            Rect rect = imageArray.get(index);
            rect.left = imgWidth * (index % LIST);
            rect.right = rect.left + imgWidth;
            rect.top = imgHeight * data;
            rect.bottom = rect.top + imgHeight;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap == null) {
            return;
        }
        for (int index = 0; index < COUNT; index++) {
            canvas.drawBitmap(bitmap, imageArray.get(index), viewArray.get(index), paint);
        }
        for (int index = 0; index < 2; index++) {
            canvas.drawBitmap(bitmap, imageFun.get(index), viewFun.get(index), paint);
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
