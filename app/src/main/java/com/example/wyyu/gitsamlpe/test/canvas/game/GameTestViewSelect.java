package com.example.wyyu.gitsamlpe.test.canvas.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.util.UIUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Random;

public class GameTestViewSelect extends View {

    public GameTestViewSelect(Context context) {
        super(context);
        initView();
    }

    public GameTestViewSelect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GameTestViewSelect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @IntDef({ShowType.SHOW_IMG, ShowType.SELECT_IMG, ShowType.SHOW_LINE, ShowType.SELECT_LINE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowType {
        int SHOW_IMG = 1;
        int SELECT_IMG = 11;
        int SHOW_LINE = 2;
        int SELECT_LINE = 22;
    }

    private static final int[] IMAGE_ARRAY = new int[]{0, 1, 3, 4, 5, 14, 16, 18, 19, 20, 21, 22, 24};

    private static final int RES_ID = R.mipmap.image_test_1;
    private static final int COUNT = 5 * 5;

    private static final int DIVIDE = 12;
    private static final int LIST = 5;
    private static final int ROW = 5;

    private static final float SIZE_STROKE = UIUtils.dpToPx(1.0f);
    private static final int COLOR_PATH_N = 0x0a000000;
    private static final int COLOR_PATH_S = 0xffb7d28d;

    private Paint paintImage;
    private Paint paintPath;
    private Bitmap bitmap;
    private Path path;

    private SparseArray<RectF> viewArray;
    private SparseArray<Rect> imageArray;
    private int[] indexArray;
    // 1-展示图片，11-选中图片，2-展示框框，22-选中框框
    private int[] showArray;

    private void initView() {
        initObject();
        initData();
    }

    private void initObject() {
        bitmap = BitmapFactory.decodeResource(getResources(), RES_ID);
        paintImage = new Paint();

        paintPath = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintPath.setStyle(Paint.Style.STROKE);
        paintPath.setStrokeWidth(SIZE_STROKE);

        path = new Path();
    }

    private void initData() {
        imageArray = new SparseArray<>();
        viewArray = new SparseArray<>();
        indexArray = new int[COUNT];
        showArray = new int[COUNT];

        for (int index = 0; index < COUNT; index++) {
            viewArray.put(index, new RectF());
            imageArray.put(index, new Rect());
            indexArray[index] = index;
            showArray[index] = getShowType(index);
        }

        Random random = new Random();
        int index = COUNT - 2;

        while (index > 0) {
            int data = random.nextInt(index);

            int value = indexArray[data];
            indexArray[data] = indexArray[index + 1];
            indexArray[index + 1] = value;
            index--;
        }
    }

    private int getShowType(int index) {
        for (int value : IMAGE_ARRAY) {
            if (value == index) {
                return ShowType.SHOW_IMG;
            }
        }
        return ShowType.SHOW_LINE;
    }

    @Override
    public void onMeasure(int valueMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(valueMeasureSpec, valueMeasureSpec);

        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();

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

            RectF rectF = viewArray.get(index);
            rectF.left = (viewWidth + DIVIDE) * (index % LIST);
            rectF.right = rectF.left + viewWidth;
            rectF.top = (viewHeight + DIVIDE) * ((int) (index / LIST));
            rectF.bottom = rectF.top + viewHeight;

            Rect rect = imageArray.get(index);
            rect.left = imgWidth * (index % LIST);
            rect.right = rect.left + imgWidth;
            rect.top = imgHeight * ((int) (index / LIST));
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
            switch (showArray[index]) {
                case ShowType.SHOW_IMG:
                    canvas.drawBitmap(bitmap, imageArray.get(indexArray[index]), viewArray.get(index), paintImage);
                    break;
                case ShowType.SELECT_IMG:
                    RectF rectS = viewArray.get(index);
                    canvas.drawBitmap(bitmap, imageArray.get(indexArray[index]), rectS, paintImage);
                    paintPath.setStyle(Paint.Style.STROKE);
                    paintPath.setColor(COLOR_PATH_S);
                    path.reset();
                    path.addRect(rectS, Path.Direction.CCW);
                    canvas.drawPath(path, paintPath);
                    break;
                case ShowType.SHOW_LINE:
                    paintPath.setStyle(Paint.Style.STROKE);
                    paintPath.setColor(COLOR_PATH_N);
                    path.reset();
                    path.addRect(viewArray.get(index), Path.Direction.CCW);
                    canvas.drawPath(path, paintPath);
                    break;
                case ShowType.SELECT_LINE:
                    paintPath.setStyle(Paint.Style.STROKE);
                    paintPath.setColor(COLOR_PATH_S);
                    path.reset();
                    path.addRect(viewArray.get(index), Path.Direction.CCW);
                    canvas.drawPath(path, paintPath);
                    break;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            performClick();
        }
        judgeClickCard(event);
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private void judgeClickCard(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        for (int index = 0; index < COUNT; index++) {
            RectF rectF = viewArray.get(index);
            if (rectF.left < x && rectF.right > x && rectF.top < y && rectF.bottom > y) {
                checkClickIndex(index);
                break;
            }
        }
    }

    private void checkClickIndex(int index) {
        if (showArray[index] == 1) {
            return;
        }
        int selectedIndex = checkSelectedIndex();
        if (selectedIndex == index) {
            return;
        }
        if (selectedIndex == -1) {
            showArray[index] = GameTestViewLine.ShowType.SELECTED;
        } else if (indexArray[index] == indexArray[selectedIndex]) {
            showArray[selectedIndex] = GameTestViewLine.ShowType.SHOW_IMG;
            showArray[index] = GameTestViewLine.ShowType.SHOW_IMG;
        } else {
            showArray[selectedIndex] = GameTestViewLine.ShowType.SHOW_NUM;
            showArray[index] = GameTestViewLine.ShowType.SELECTED;
        }
        invalidate();
    }

    private int checkSelectedIndex() {
        for (int index = 0; index < COUNT; index++) {
            if (showArray[index] == GameTestViewLine.ShowType.SELECTED) {
                return index;
            }
        }
        return -1;
    }
}