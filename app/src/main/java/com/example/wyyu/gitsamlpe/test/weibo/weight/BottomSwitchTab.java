package com.example.wyyu.gitsamlpe.test.weibo.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/4/17.
 **/

public class BottomSwitchTab extends LinearLayout implements View.OnClickListener {

    public BottomSwitchTab(Context context) {
        super(context);
        initBottomTab();
    }

    public BottomSwitchTab(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initBottomTab();
    }

    public BottomSwitchTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBottomTab();
    }

    private static final int[] ICON_NORMAL =
        new int[] { R.mipmap.ic_home_normal, R.mipmap.ic_find_normal, R.mipmap.ic_news_normal };
    private static final int[] ICON_SELECT =
        new int[] { R.mipmap.ic_home_select, R.mipmap.ic_find_select, R.mipmap.ic_news_select };
    private static final int ICON_COUNT = 3;

    private OnSwitchTabClickListener switchTabClickListener;

    private ImageView[] iconArray;
    private int presentIndex;

    private void initBottomTab() {

        LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_switch_tab, this);

        iconArray = new ImageView[ICON_COUNT];

        iconArray[0] = findViewById(R.id.bottom_tab_left);
        iconArray[1] = findViewById(R.id.bottom_tab_middle);
        iconArray[2] = findViewById(R.id.bottom_tab_right);

        findViewById(R.id.bottom_tab_click_left).setOnClickListener(this);
        findViewById(R.id.bottom_tab_click_middle).setOnClickListener(this);
        findViewById(R.id.bottom_tab_click_right).setOnClickListener(this);

        presentIndex = 0;
        refreshSelectStatus(0);
    }

    public void refreshSelectStatus(int selectIndex) {
        iconArray[presentIndex].setImageResource(ICON_NORMAL[presentIndex]);
        iconArray[selectIndex].setImageResource(ICON_SELECT[selectIndex]);
        presentIndex = selectIndex;

        if (switchTabClickListener != null) {
            switchTabClickListener.onSwitch(presentIndex);
        }
    }

    @Override public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_tab_click_left:
                refreshSelectStatus(0);
                break;
            case R.id.bottom_tab_click_middle:
                refreshSelectStatus(1);
                break;
            case R.id.bottom_tab_click_right:
                refreshSelectStatus(2);
                break;
        }
    }

    public void setOnSwitchTabClickListener(OnSwitchTabClickListener switchTabClickListener) {
        this.switchTabClickListener = switchTabClickListener;
    }

    public interface OnSwitchTabClickListener {

        void onSwitch(int index);
    }
}
