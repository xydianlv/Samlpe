package com.example.wyyu.gitsamlpe.test.function.detector;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.example.wyyu.gitsamlpe.framework.ULog;

/**
 * Created by wyyu on 2019/2/26.
 **/

public class DetectorTestView extends View {

    public DetectorTestView(Context context) {
        this(context, null);
    }

    public DetectorTestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DetectorTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTestView();
    }

    private GestureDetector gestureDetector;

    private void initTestView() {
        gestureDetector =
            new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {

                // 用户按下屏幕的时候回调
                @Override public boolean onDown(MotionEvent e) {
                    ULog.show("DetectorTest -> onDown");
                    return true;
                }

                // 用户在按下 100ms 后无松开和位移就会调用该回调，该回调触发后依然会触发其他的回调
                @Override public void onShowPress(MotionEvent e) {
                    ULog.show("DetectorTest -> onShowPress");
                }

                // 用户手指松开时没有执行 onScroll 和 onLongPress 这两回调的话就会调用这个回调
                @Override public boolean onSingleTapUp(MotionEvent e) {
                    ULog.show("DetectorTest -> onSingleTapUp");
                    return false;
                }

                // 手指滑动的时候调用的回调
                @Override public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                    float distanceY) {
                    ULog.show("DetectorTest -> onScroll");
                    return false;
                }

                // 用户长按时触发，触发之后就不会再触发其他回调，直至松开
                @Override public void onLongPress(MotionEvent e) {
                    ULog.show("DetectorTest -> onLongPress");
                }

                // 执行抛操作时的回调，MOVE 执行完后手松开的一瞬间，沿 X 或 Y 方向移动达到一定数值视为抛操作
                @Override public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                    float velocityY) {
                    ULog.show("DetectorTest -> onFling");
                    return false;
                }
            });
        gestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {

            // 一个明确的单击事件的回调 < 300ms 内没有下一个 Down 操作 >
            @Override public boolean onSingleTapConfirmed(MotionEvent e) {
                ULog.show("DetectorTest -> onSingleTapConfirmed");
                return false;
            }

            // 一个确认的双击事件的回调
            @Override public boolean onDoubleTap(MotionEvent e) {
                ULog.show("DetectorTest -> onDoubleTap");
                return false;
            }

            // 在 onDoubleTap 回调之后执行的事件都会回调这个方法，比如双击后未松手进行拖拽操作
            @Override public boolean onDoubleTapEvent(MotionEvent e) {
                ULog.show("DetectorTest -> onDoubleTapEvent");
                return false;
            }
        });
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private OnDetectorTapListener detectorTapListener;

    public void setDetectorTapListener(OnDetectorTapListener detectorTapListener) {
        this.detectorTapListener = detectorTapListener;
    }

    public interface OnDetectorTapListener {

        boolean onPressDown();

        boolean onDoubleClick();

        boolean onLongClick();
    }
}
