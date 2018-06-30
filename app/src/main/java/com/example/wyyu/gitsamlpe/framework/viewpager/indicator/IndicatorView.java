package com.example.wyyu.gitsamlpe.framework.viewpager.indicator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2018/6/29.
 **/

public class IndicatorView extends LinearLayout {

    private static final int VIEW_SIZE = UIUtils.dpToPx(48);
    private int selectColor;
    private int backColor;
    private int presentSelectIndex;

    public IndicatorView(Context context) {
        super(context);
        initIndicatorView();
    }
    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initIndicatorView();
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initIndicatorView();
    }

    private void initIndicatorView() {
        setOrientation(LinearLayout.HORIZONTAL);
    }

    private View getElementView(boolean isFirst) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(VIEW_SIZE, VIEW_SIZE);

        params.setMargins(isFirst ? 0 : UIUtils.dpToPx(9), 0, 0, 0);

        CircleView view = new CircleView(getContext());

        view.setLayoutParams(params);
        view.setCircleRadius(VIEW_SIZE / 2);
        view.setCircleColor(backColor);

        return view;
    }

    /**
     * 设置滑动标识器的基本数据
     *
     * @param elementNum  可滑动 item 的个数
     * @param backColor   item 背景色
     * @param selectColor item 选中时的颜色
     */
    public void setIndicator(int elementNum, int backColor, int selectColor) {

        removeAllViews();

        if (elementNum <= 1) return;

        this.selectColor = selectColor;
        this.backColor = backColor;

        for (int index = 0; index < elementNum; index++) {
            addView(getElementView(index == 0), index);
        }

        refreshIndicator(0);
    }

    public void refreshIndicator(int currentIndex) {

        if (getChildAt(presentSelectIndex) == null || getChildAt(currentIndex) == null) return;

        ((CircleView) getChildAt(presentSelectIndex)).setCircleColor(backColor);
        ((CircleView) getChildAt(currentIndex)).setCircleColor(selectColor);

        presentSelectIndex = currentIndex;
    }
}
