package com.example.wyyu.gitsamlpe.test.bigimage.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.wyyu.gitsamlpe.test.bigimage.data.LocationData;

/**
 * Created by wyyu on 2018/5/8.
 **/

public class ImageFunView extends SubsamplingScaleImageView {

    public ImageFunView(Context context) {
        super(context);
    }

    public ImageFunView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private LocationData locationData;

    public void setData(LocationData locationData) {
        this.locationData = locationData;
        requestLayout();
    }

    /**
     * onLayout 的四个参数 表示 该自定义控件在父布局中的位置
     */
    @Override public void layout(int l, int t, int r, int b) {
        if (locationData == null) {
            super.layout(l, t, r, b);
            return;
        }
        super.layout(locationData.left, locationData.top, locationData.right, locationData.bottom);
    }
}
