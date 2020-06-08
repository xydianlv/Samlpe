package com.example.wyyu.gitsamlpe.test.text.xfermode;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.wyyu.gitsamlpe.util.UIUtils;
import static android.graphics.Canvas.ALL_SAVE_FLAG;

/**
 * Created by wyyu on 2020-06-08.
 **/

public class OverDecoration extends RecyclerView.ItemDecoration {

    private Xfermode xfermode;
    private Paint paint;

    private boolean firstDraw;
    private int layerId;

    OverDecoration() {
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        paint = new Paint();

        firstDraw = true;
    }

    @Override public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent,
        @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        RectF rect = new RectF(0, 0, parent.getWidth(), parent.getHeight());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            layerId = c.saveLayer(rect, paint);
        } else {
            layerId = c.saveLayer(rect, paint, ALL_SAVE_FLAG);
        }
    }

    @Override public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent,
        @NonNull RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
        paint.setXfermode(xfermode);
        LinearGradient gradient = new LinearGradient(0, 0, 0, UIUtils.dpToPx(64.0f), new int[] {
            Color.TRANSPARENT, Color.BLACK
        }, null, Shader.TileMode.CLAMP);
        paint.setShader(gradient);
        canvas.drawRect(0, 0, parent.getWidth(), UIUtils.dpToPx(64.0f), paint);
        paint.setXfermode(null);
        canvas.restoreToCount(layerId);

        if (firstDraw) {
            firstDraw = false;
            parent.postInvalidate();
        }
    }

    @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
        @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }
}