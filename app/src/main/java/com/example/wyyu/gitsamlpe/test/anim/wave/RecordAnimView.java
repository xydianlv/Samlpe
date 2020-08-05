package com.example.wyyu.gitsamlpe.test.anim.wave;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.wyyu.gitsamlpe.framework.ULog;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wyyu on 2018/1/11.
 **/

public class RecordAnimView extends View {

    public RecordAnimView(Context context) {
        super(context);
        initAnimView();
    }

    public RecordAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAnimView();
    }

    public RecordAnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAnimView();
    }

    private static final long SPEED = 2;

    private WaveData waveDataA;
    private WaveData waveDataB;
    private WaveData waveDataC;

    private Task timerTask;
    private Timer timer;

    @SuppressLint("HandlerLeak") private Handler handler = new Handler() {

        @Override public void handleMessage(Message msg) {

            waveDataA.displacement = waveDataA.displacement + waveDataA.moveLength;

            if (waveDataA.displacement > waveDataA.waveWidth) {
                waveDataA.displacement = 0;
            }

            waveDataB.displacement = waveDataB.displacement + waveDataB.moveLength;

            if (waveDataB.displacement > waveDataB.waveWidth) {
                waveDataB.displacement = 0;
            }

            waveDataC.displacement = waveDataC.displacement + waveDataC.moveLength;

            if (waveDataC.displacement > waveDataC.waveWidth) {
                waveDataC.displacement = 0;
            }

            invalidate();
        }
    };

    private void initAnimView() {

        waveDataA = new WaveData(0x66ff0000, 4, 2.0f);
        waveDataB = new WaveData(0x22ff0000, 6, 1.4f);
        waveDataC = new WaveData(0xbbf5878a, 3, 3.0f);
    }

    @SuppressLint("DrawAllocation") @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        float viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        float viewWidth = MeasureSpec.getSize(widthMeasureSpec);

        waveDataA.levelHeight = viewHeight / 2;
        waveDataA.waveHeight = viewHeight / 2;
        waveDataA.waveWidth = viewWidth;
        initWaveData(waveDataA);

        waveDataB.levelHeight = viewHeight / 2;
        waveDataB.waveHeight = viewHeight / 3;
        waveDataB.waveWidth = viewWidth;
        initWaveData(waveDataB);

        waveDataC.levelHeight = viewHeight / 2;
        waveDataC.waveHeight = viewHeight / 2;
        waveDataC.waveWidth = viewWidth;
        initWaveData(waveDataC);
    }

    private void initWaveData(WaveData waveData) {

        float valueY;
        float valueX;

        for (int index = 0; index < 9; index++) {
            if (index % 2 == 0) {
                valueY = waveData.levelHeight;
            } else if (index % 4 == 1) {
                valueY = waveData.levelHeight - waveData.waveHeight;
            } else {
                valueY = waveData.levelHeight + waveData.waveHeight;
            }

            valueX = waveData.waveWidth / 4 * index;

            waveData.pointListLine.add(new Point(valueX, valueY));
        }
    }

    @Override protected void onDraw(Canvas canvas) {

        ULog.show("LevelHeight : " + "onDraw");

        drawAppointedWave(canvas, waveDataA);
        drawAppointedWave(canvas, waveDataB);
        drawAppointedWave(canvas, waveDataC);
    }

    private void drawAppointedWave(Canvas canvas, WaveData waveData) {

        waveData.pathLine.reset();
        waveData.pathLine.moveTo(waveData.pointListLine.get(0).x - waveData.displacement,
            waveData.levelHeight * 2 + waveData.waveHeight);
        waveData.pathLine.lineTo(waveData.pointListLine.get(0).x - waveData.displacement,
            waveData.levelHeight);
        for (int i = 0; i < 4; i++) {
            waveData.pathLine.quadTo(
                waveData.pointListLine.get(1 + 2 * i).x - waveData.displacement,
                waveData.pointListLine.get(1 + 2 * i).y,
                waveData.pointListLine.get(2 + 2 * i).x - waveData.displacement,
                waveData.pointListLine.get(2 + 2 * i).y);
        }
        waveData.pathLine.lineTo(waveData.pointListLine.get(8).x - waveData.displacement,
            waveData.levelHeight * 2 + waveData.waveHeight);
        waveData.pathLine.close();
        canvas.drawPath(waveData.pathLine, waveData.paintLine);
    }

    public void startMove() {

        stopMove();

        timerTask = new Task(handler);

        timer = new Timer();

        timer.schedule(timerTask, 0, SPEED);
    }

    public void stopMove() {

        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    class Task extends TimerTask {

        Handler mHandler;

        Task(Handler handler) {
            mHandler = handler;
        }

        @Override public void run() {
            handler.sendMessage(handler.obtainMessage());
        }
    }

    private class WaveData {

        // 构成曲线的点的集合
        List<Point> pointListLine;

        // 波宽
        float waveHeight;
        // 波长
        float waveWidth;

        // 画笔 Paint
        Paint paintLine;
        // 路径 Path
        Path pathLine;

        // 基准线高度
        float levelHeight;
        // 每次重绘时的位移距离
        float moveLength;

        // 移动总距离
        float displacement;

        WaveData(int color, int width, float moveLength) {

            pointListLine = new ArrayList<>(9);
            paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
            pathLine = new Path();

            paintLine.setStyle(Paint.Style.STROKE);
            paintLine.setStrokeWidth(width);
            paintLine.setColor(color);

            this.moveLength = moveLength;
        }
    }

    private class Point {
        float x;
        float y;

        Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
