package com.example.wyyu.gitsamlpe.test.pager.blog.weight;

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

    private static final int[] ICON_NORMAL = new int[] {
        R.mipmap.icon_main_tab_home, R.mipmap.icon_main_tab_find, R.mipmap.icon_main_tab_msg,
        R.mipmap.icon_main_tab_me
    };
    private static final int[] ICON_SELECT = new int[] {
        R.mipmap.icon_main_tab_home, R.mipmap.icon_main_tab_find, R.mipmap.icon_main_tab_msg,
        R.mipmap.icon_main_tab_me
    };

    private static final int ICON_COUNT = 4;

    private OnSwitchTabClickListener switchTabClickListener;

    private ImageView[] iconArray;
    private int presentIndex;

    private void initBottomTab() {

        LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_switch_tab, this);

        iconArray = new ImageView[ICON_COUNT];

        iconArray[0] = findViewById(R.id.bottom_tab_a);
        iconArray[1] = findViewById(R.id.bottom_tab_b);
        iconArray[2] = findViewById(R.id.bottom_tab_c);
        iconArray[3] = findViewById(R.id.bottom_tab_d);

        findViewById(R.id.bottom_tab_click_a).setOnClickListener(this);
        findViewById(R.id.bottom_tab_click_b).setOnClickListener(this);
        findViewById(R.id.bottom_tab_click_c).setOnClickListener(this);
        findViewById(R.id.bottom_tab_click_d).setOnClickListener(this);

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
            case R.id.bottom_tab_click_a:
                refreshSelectStatus(0);
                break;
            case R.id.bottom_tab_click_b:
                refreshSelectStatus(1);
                break;
            case R.id.bottom_tab_click_c:
                refreshSelectStatus(2);
                break;
            case R.id.bottom_tab_click_d:
                refreshSelectStatus(3);
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
