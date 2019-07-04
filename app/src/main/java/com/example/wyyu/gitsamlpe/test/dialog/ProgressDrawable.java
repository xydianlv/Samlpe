package com.example.wyyu.gitsamlpe.test.dialog;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2019-07-04.
 **/
public class ProgressDrawable extends Drawable {

    private float percent;

    private RectF rectF;
    private Paint paint;

    ProgressDrawable() {

        rectF = new RectF(6, 6, UIUtils.dpToPx(40.0f), UIUtils.dpToPx(40.0f));
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        percent = 0.0f;
    }

    @Override public void draw(@NonNull Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(UIUtils.dpToPx(3));

        paint.setColor(0xff000000);
        canvas.drawArc(rectF, 0, 360, false, paint);

        paint.setColor(0xffff0000);
        canvas.drawArc(rectF, -90, 360 * (percent / 100), false, paint);

        paint.setStrokeWidth(UIUtils.dpToPx(1));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(0xffff0000);
        paint.setTextSize(UIUtils.dpToPx(12.0f));
        canvas.drawText(String.format("%s%%", String.valueOf((int) percent)), 60.0f, 78.0f, paint);
    }

    @Override public void setAlpha(int alpha) {

    }

    @Override public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    // 返回 Drawable 获得的不透明度
    // OPAQUE 完全不透明，遮盖下面的所有内容
    // TRANSPARENT 透明，完全不显示任何东西
    // TRANSLUCENT 绘制的地方遮盖底下的内容
    @Override public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override protected boolean onLevelChange(int level) {
        percent = level;
        invalidateSelf();
        return false;
    }
}
