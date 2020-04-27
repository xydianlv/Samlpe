package com.example.wyyu.gitsamlpe.test.text.anim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.example.wyyu.gitsamlpe.util.UIUtils;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wyyu on 2020-01-06.
 **/

public class MarqueeSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    public MarqueeSurfaceView(Context context) {
        super(context);
        initSurface();
    }

    public MarqueeSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSurface();
    }

    public MarqueeSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSurface();
    }

    private static class TextStruct {

        private String name;
        private String text;

        private float nameIndex;
        private float textIndex;

        private TextStruct(String nameValue, String textValue, float nameIndex, float textIndex) {
            this.name = nameValue;
            this.text = textValue;
            this.nameIndex = nameIndex;
            this.textIndex = textIndex;
        }
    }

    private static final int STRUCT_DIVIDE = UIUtils.dpToPx(32.0f);
    private static final int SPACE_DIVIDE = UIUtils.dpToPx(2.0f);
    private static final int MSG_DEFAULT = 0;

    private Handler handler;
    private Paint paintName;
    private Paint paintText;
    private Rect rect;

    private List<TextStruct> structList;
    private float allLength;
    private float x;
    private float y;

    private HandlerThread thread;
    private Canvas canvas;

    private void initSurface() {
        initBasicValue();
        initPaintName();
        initPaintText();
        startSurface();
    }

    private void initBasicValue() {
        structList = new LinkedList<>();
        allLength = 0.0f;

        y = UIUtils.dpToPx(22.0f) * 1.0f;
        x = 40.0f;

        rect = new Rect();

        for (int index = 0; index < 4; index++) {
            addNextValue("NameIndex" + index, "新年快乐！新年快乐！！");
        }
    }

    public void addNextValue(String nameValue, String textValue) {
        if (TextUtils.isEmpty(nameValue) || TextUtils.isEmpty(textValue)) {
            return;
        }
        nameValue = nameValue + "：";

        float nameIndex = allLength == 0.0f ? 0.0f : allLength + STRUCT_DIVIDE;
        float textIndex = nameIndex + getContentWidth(nameValue, true) + SPACE_DIVIDE;

        this.allLength = textIndex + getContentWidth(textValue, false);

        if (this.structList == null) {
            this.structList = new LinkedList<>();
        }
        this.structList.add(new TextStruct(nameValue, textValue, nameIndex, textIndex));
    }

    private float getContentWidth(String textValue, boolean forName) {
        if (TextUtils.isEmpty(textValue)) {
            return 0.0f;
        }
        if (rect == null) {
            rect = new Rect();
        }
        if (forName) {
            if (paintName == null) {
                initPaintName();
            }
            paintName.getTextBounds(textValue, 0, textValue.length(), rect);
        } else {
            if (paintText == null) {
                initPaintText();
            }
            paintText.getTextBounds(textValue, 0, textValue.length(), rect);
        }
        return rect.width();
    }

    private void initPaintName() {
        paintName = new Paint();
        paintName.setTextSize(UIUtils.dpToPx(12.0f));
        paintName.setColor(0xffB27100);
        paintName.setAntiAlias(true);
        paintName.setStyle(Paint.Style.FILL_AND_STROKE);
        paintName.setStrokeWidth(0.4f);
        paintName.setTextAlign(Paint.Align.LEFT);
    }

    private void initPaintText() {
        paintText = new Paint();
        paintText.setTextSize(UIUtils.dpToPx(12.0f));
        paintText.setColor(0xffB27100);
        paintText.setAntiAlias(true);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setTextAlign(Paint.Align.LEFT);
    }

    private void startSurface() {
        setZOrderOnTop(true);
        getHolder().addCallback(this);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override public void surfaceCreated(SurfaceHolder holder) {
        initAndStart();
    }

    @Override public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override public void surfaceDestroyed(SurfaceHolder holder) {
        onScrollEnd();
    }

    private void initAndStart() {
        thread = new HandlerThread("MarqueeHandlerThread");
        thread.start();

        handler = new Handler(thread.getLooper()) {
            @Override public void handleMessage(Message msg) {
                if (msg == null || getHolder() == null) {
                    return;
                }
                canvas = getHolder().lockCanvas();
                if (canvas == null) {
                    return;
                }
                if (structList == null || structList.isEmpty()) {
                    return;
                }

                Log.e("MarqueeSurfaceTest", "handleMessage -> x : " + x);

                canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                for (TextStruct struct : structList) {
                    canvas.drawText(struct.name, x + struct.nameIndex, y, paintName);
                    canvas.drawText(struct.text, x + struct.textIndex, y, paintText);
                }
                getHolder().unlockCanvasAndPost(canvas);
                x = x - 1.0f;

                if (x < -allLength) {
                    onScrollEnd();
                    return;
                }

                if (handler != null) {
                    handler.sendEmptyMessageDelayed(MSG_DEFAULT, 10);
                }
            }
        };
        handler.sendEmptyMessageDelayed(MSG_DEFAULT, 10);
    }

    private void onScrollEnd() {
        if (structList != null) {
            structList.clear();
        }

        if (handler != null) {
            handler.removeMessages(MSG_DEFAULT);
        }
        if (thread != null) {
            thread.quit();
        }

        handler = null;
        thread = null;

        allLength = 0.0f;
        x = 40.0f;
    }
}
