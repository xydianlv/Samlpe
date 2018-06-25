package com.example.wyyu.gitsamlpe.test.slide.indicator;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatTextView;
import android.widget.FrameLayout;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/6/25.
 **/

public class IndicatorTitleView extends FrameLayout implements IMeasurablePagerTitleView {

    private AppCompatTextView title;
    private @ColorInt int mSelectedColor;
    private @ColorInt int mNormalColor;

    public IndicatorTitleView(Context context) {
        super(context, null);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.layout_indicator_title_view, this);
        title = (AppCompatTextView) findViewById(R.id.title);
    }

    @Override public void onSelected(int index, int totalCount) {
        if (title != null) {
            title.setTextColor(mSelectedColor);
        }
    }

    @Override public void onDeselected(int index, int totalCount) {
        if (title != null) {
            title.setTextColor(mNormalColor);
        }
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
    }

    @Override public int getContentLeft() {
        return getLeft();
    }

    @Override public int getContentTop() {
        return getTop();
    }

    @Override public int getContentRight() {
        return getRight();
    }

    @Override public int getContentBottom() {
        return getBottom();
    }

    public int getSelectedColor() {
        return mSelectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        mSelectedColor = selectedColor;
    }

    public int getNormalColor() {
        return mNormalColor;
    }

    public void setNormalColor(int normalColor) {
        mNormalColor = normalColor;
    }

    public void setText(String text) {
        if (title != null) {
            title.setText(text);
        }
    }

    public void setTextSize(float textSize){
        if(title!=null){
            title.setTextSize(textSize);
        }
    }
}
