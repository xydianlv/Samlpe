package com.example.wyyu.gitsamlpe.test.text.gradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2020-05-12.
 **/

public class GradientTextView extends AppCompatTextView {

    public GradientTextView(Context context) {
        super(context);
        initTextView();
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTextView();
    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTextView();
    }

    private LinearGradient gradient;
    private Xfermode xfermode;

    private Paint paint;

    private void initTextView() {
        gradient = new LinearGradient(0.0f, 0.0f, 0.0f, UIUtils.dpToPx(48.0f), Color.TRANSPARENT,
            Color.BLACK, Shader.TileMode.CLAMP);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        paint.setXfermode(xfermode);
        paint.setShader(gradient);

        canvas.drawRect(0, 0, UIUtils.getScreenWidth(), UIUtils.dpToPx(48.0f), paint);

        paint.setXfermode(null);

        canvas.restoreToCount(layerId);
    }
}
