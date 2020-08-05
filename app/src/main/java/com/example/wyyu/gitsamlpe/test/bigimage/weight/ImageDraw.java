package com.example.wyyu.gitsamlpe.test.bigimage.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.wyyu.gitsamlpe.test.bigimage.ActivityImageList;

/**
 * Created by wyyu on 2018/4/12.
 **/

public class ImageDraw extends View {

    private Paint mPaint;

    private ActivityImageList.ImageLocationData locationData;

    public ImageDraw(Context context) {
        super(context);
        initImageDraw();
    }

    public ImageDraw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initImageDraw();
    }

    public ImageDraw(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initImageDraw();
    }

    private void initImageDraw() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int color = Color.GREEN;

        mPaint.setColor(color);
    }

    public void setData(ActivityImageList.ImageLocationData locationData) {
        this.locationData = locationData;
    }

    /**
     * onLayout 的四个参数 表示 该自定义控件在父布局中的位置
     */
    @Override public void layout(int l, int t, int r, int b) {
        super.layout(locationData.left, locationData.top, locationData.right, locationData.bottom);
    }

    /**
     * drawRect() 方法参数
     * 前两个参数为 Paint 对象绘制在 layout 确定的画布中 的左上角的坐标
     * 中间两个参数是待描绘区域的 宽 和 高
     * 最后参数是设置好的 Paint 对象
     */
    @Override protected void onDraw(Canvas canvas) {

        canvas.drawRect(0, 0, locationData.right - locationData.left,
            locationData.bottom - locationData.top, mPaint);
    }
}
