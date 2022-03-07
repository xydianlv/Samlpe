package com.example.wyyu.gitsamlpe.test.canvas.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.wyyu.gitsamlpe.R;

import java.util.Random;

public class GameTestViewMove extends View {

    public GameTestViewMove(Context context) {
        super(context);
        initView();
    }

    public GameTestViewMove(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GameTestViewMove(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private static final int RES_ID = R.mipmap.image_test_1;
    private static final int DIVIDE = 12;
    private static final int LIST = 3;
    private static final int ROW = 3;
    private static final int COUNT = LIST * ROW;

    private Bitmap bitmap;
    private Paint paint;

    private SparseArray<RectF> viewArray;
    private SparseArray<Rect> imageArray;
    private int[] indexArray;
    private boolean hasSort;

    private void initView() {
        initObject();
        initData();
    }

    private void initObject() {
        bitmap = BitmapFactory.decodeResource(getResources(), RES_ID);
        paint = new Paint();
    }

    private void initData() {
        imageArray = new SparseArray<>();
        viewArray = new SparseArray<>();
        indexArray = new int[COUNT];

        for (int index = 0; index < COUNT; index++) {
            viewArray.put(index, new RectF());
            imageArray.put(index, new Rect());
            indexArray[index] = index;
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
        hasSort = false;
    }
}
