package com.example.wyyu.gitsamlpe.framework.contact;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2018/6/28.
 **/

public class ContactView extends View {

    public int width;

    public int height;

    private Bitmap bitmap;

    public ContactView(Context context) {
        super(context);
        init();
    }

    public ContactView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ContactView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_news_select);
        width = UIUtils.dpToPx(240.0f);
        height = width;
        //将图片裁剪到指定大小
        bitmap = Bitmap.createScaledBitmap(src, width, height, true);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, height);
    }

    @Override protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, null);
    }
}
