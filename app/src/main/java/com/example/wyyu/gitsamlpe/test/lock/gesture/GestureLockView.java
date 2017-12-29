package com.example.wyyu.gitsamlpe.test.lock.gesture;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wyyu.gitsamlpe.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class GestureLockView extends LinearLayout {

    // 可连接的节点图标
    private static final int[] CLICK_ICON = new int[]{R.id.lock_icon_0, R.id.lock_icon_1, R.id.lock_icon_2
            , R.id.lock_icon_3, R.id.lock_icon_4, R.id.lock_icon_5, R.id.lock_icon_6, R.id.lock_icon_7, R.id.lock_icon_8};

    // 选中的 节点 的 Index 列表
    private List<Integer> selectIndexList;
    // 所有节点 的 数据集合
    private List<NodeData> nodeList;

    private Paint paint;
    private Path path;

    // 第一行节点 所在的 父控件 的高度
    private int lineHeightOne;
    // 第二行节点 所在的 父控件 的高度
    private int lineHeightTwo;

    // 该 View 距离屏幕左边界的 距离
    private int parentLeft;
    // 该 View 距离屏幕上边界的 距离
    private int parentTop;

    // 是否处于展示绘制结果的时间段，在此期间，屏幕不可点击
    private boolean showResult;

    public GestureLockView(Context context) {
        super(context);
        initGestureLockView();
    }

    public GestureLockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initGestureLockView();
    }

    public GestureLockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGestureLockView();
    }

    private void initGestureLockView() {
        LayoutInflater.from(getContext()).inflate(R.layout.gesture_lock_view, this);

        setBackgroundColor(0xffffffff);

        post(new Runnable() {
            @Override
            public void run() {
                initBasicData();
            }
        });

        initDrawTool();

        showResult = false;

    }

    public void initBasicData() {

        lineHeightOne = findViewById(R.id.gesture_line_one).getHeight();
        lineHeightTwo = findViewById(R.id.gesture_line_two).getHeight();

        int[] number = new int[2];
        getLocationOnScreen(number);

        parentLeft = number[0];
        parentTop = number[1];

        selectIndexList = new ArrayList<>();
        nodeList = new ArrayList<>();

        for (int index = 0; index < CLICK_ICON.length; index++) {
            nodeList.add(new NodeData((ImageView) findViewById(CLICK_ICON[index]), index));
        }
    }

    private void initDrawTool() {
        paint = new Paint();

        paint.setAntiAlias(true);

        paint.setColor(0xff007ee5);

        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeWidth(4);

        path = new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        canvas.drawPath(path, paint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (showResult) return false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                resetLockView();
            case MotionEvent.ACTION_MOVE:
                checkNodeSelected(event.getRawX(), event.getRawY());
                refreshDrawData(event.getRawX(), event.getRawY(), false);
                return true;
            case MotionEvent.ACTION_UP:
                refreshDrawData(event.getRawX(), event.getRawY(), true);
                checkPassword();
                return true;
            default:
                return false;
        }
    }

    // 检查 当前按压点 是否属于节点区域
    private void checkNodeSelected(float pressX, float pressY) {

        boolean isSelect = false;

        for (NodeData nodeData : nodeList) {
            if (pressX > nodeData.left && pressX < nodeData.right && pressY > nodeData.top && pressY < nodeData.bottom) {
                for (int index : selectIndexList) {
                    if (index == nodeData.index) {
                        isSelect = true;
                        break;
                    }
                }

                if (isSelect) {
                    break;
                }

                nodeData.imageView.setImageResource(R.mipmap.lock_press);
                selectIndexList.add(nodeData.index);

                checkMiddleNode();

                break;
            }
        }
    }

    private void checkMiddleNode() {

        if (selectIndexList.size() > 1) {
            int nodeLastIndex = selectIndexList.get(selectIndexList.size() - 1);
            int nodePreIndex = selectIndexList.get(selectIndexList.size() - 2);

            if ((nodeLastIndex + nodePreIndex) % 2 == 0) {

                int nodeMidIndex = (nodeLastIndex + nodePreIndex) / 2;

                int nodeLastX = nodeList.get(nodeLastIndex).nodeX;
                int nodeLastY = nodeList.get(nodeLastIndex).nodeY;

                int nodeMidX = nodeList.get(nodeMidIndex).nodeX;
                int nodeMidY = nodeList.get(nodeMidIndex).nodeY;

                int nodePreX = nodeList.get(nodePreIndex).nodeX;
                int nodePreY = nodeList.get(nodePreIndex).nodeY;

                if ((nodeLastX + nodeMidX + nodePreX == nodeLastY + nodeMidY + nodePreY)
                        || (nodeLastX == nodeMidX && nodeMidX == nodePreX)
                        || (nodeLastY == nodeMidY && nodeMidY == nodePreY)) {

                    nodeList.get(nodeMidIndex).imageView.setImageResource(R.mipmap.lock_press);
                    selectIndexList.add(selectIndexList.size() - 1, nodeMidIndex);
                }
            }
        }

    }

    // 根据 当前按压点 刷新连接的折线
    private void refreshDrawData(float pressX, float pressY, boolean isLast) {

        path.reset();

        if (selectIndexList.size() != 0) {
            path.moveTo(nodeList.get(selectIndexList.get(0)).nodeX
                    , nodeList.get(selectIndexList.get(0)).nodeY);

            for (int index = 1; index < selectIndexList.size(); index++) {
                path.lineTo(nodeList.get(selectIndexList.get(index)).nodeX
                        , nodeList.get(selectIndexList.get(index)).nodeY);
            }

            if (!isLast) path.lineTo(pressX - parentLeft, pressY - parentTop);
        }

        invalidate();
    }

    // 释放按压点后，检查手势密码正确性
    private void checkPassword() {

        StringBuilder password = new StringBuilder();
        showResult = true;

        for (int value : selectIndexList) {
            password.append(value);
        }

        if (!onDrawLockComplete.onComplete(password.toString())) {

            for (int index : selectIndexList) {
                nodeList.get(index).imageView.setImageResource(R.mipmap.lock_fail);
            }
            paint.setColor(0xffff0000);

            invalidate();

            Observable
                    .create(new Observable.OnSubscribe<Boolean>() {
                        @Override
                        public void call(final Subscriber<? super Boolean> subscriber) {
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    subscriber.onNext(true);
                                }
                            }, 1000);
                        }
                    })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            resetLockView();
                            showResult = false;
                        }
                    });

        } else {

            Observable
                    .create(new Observable.OnSubscribe<Boolean>() {
                        @Override
                        public void call(final Subscriber<? super Boolean> subscriber) {
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    subscriber.onNext(true);
                                }
                            }, 1000);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            resetLockView();
                            showResult = false;
                        }
                    });
        }
    }

    // 刷新界面为初始状态
    private void resetLockView() {
        for (NodeData nodeData : nodeList) {
            nodeData.imageView.setImageResource(R.mipmap.lock_normal);
        }

        selectIndexList.clear();

        paint.setColor(0xff007ee5);

        path.reset();

        invalidate();
    }


    // 节点的数据集合
    private class NodeData {
        ImageView imageView;
        int index;

        int top;
        int right;
        int left;
        int bottom;

        int nodeX;
        int nodeY;

        NodeData(ImageView imageView, int index) {

            this.imageView = imageView;
            this.index = index;

            initViewData();

            initViewNoun();
        }

        private void initViewData() {
            int[] number = new int[2];
            imageView.getLocationOnScreen(number);

            left = number[0];
            top = number[1];
            right = number[0] + imageView.getWidth();
            bottom = number[1] + imageView.getHeight();
        }

        private void initViewNoun() {
            nodeX = (imageView.getRight() + imageView.getLeft()) / 2;
            nodeY = (imageView.getBottom() + imageView.getTop()) / 2;

            if (index >= 3 && index < 6) nodeY = nodeY + lineHeightOne;

            if (index >= 6 && index < 9) nodeY = nodeY + lineHeightTwo + lineHeightOne;
        }
    }


    private OnDrawLockComplete onDrawLockComplete;

    void setOnDrawLockComplete(OnDrawLockComplete onDrawLockComplete) {
        this.onDrawLockComplete = onDrawLockComplete;
    }

    interface OnDrawLockComplete {
        boolean onComplete(String password);
    }
}
