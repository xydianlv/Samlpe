package com.example.wyyu.gitsamlpe.framework.window;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2019/3/11.
 **/

public class PopupShowTest extends FrameLayout {

    public PopupShowTest(@NonNull Context context) {
        this(context, null);
    }

    public PopupShowTest(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopupShowTest(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPopupShow();
    }

    private PopupWindow popupWindow;
    private FrameLayout container;

    private int screenHeight;
    private int screenWidth;

    private void initPopupShow() {

        LayoutInflater.from(getContext()).inflate(R.layout.layout_popup_root, this);

        PressListenerView listenerView = findViewById(R.id.pop_press);
        listenerView.setOnPressListener(new PressListenerView.OnPressListener() {
            @Override public boolean onPressDown() {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }

            @Override public boolean onPressUp() {
                return false;
            }
        });

        container = findViewById(R.id.pop_container);

        screenHeight = UIUtils.getScreenHeight();
        screenWidth = UIUtils.getScreenWidth();
    }

    public void show(View anchorView, View contentView, @ShowType int showType) {
        if (anchorView == null || contentView == null) {
            return;
        }

        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);

        //location[1] = location[1] - (int) getResources().getDimension(R.dimen.status_bar_height);

        int height = contentView.getHeight();
        int width = contentView.getWidth();

        switch (showType) {
            case ShowType.左上角:
                showContentView(anchorView, contentView, location[0] - width, location[1] - height);
                break;
            case ShowType.左下角:
                showContentView(anchorView, contentView, location[0] - width,
                    location[1] + height + anchorView.getHeight());
                break;
            case ShowType.右上角:
                showContentView(anchorView, contentView,
                    location[0] + anchorView.getWidth() + width, location[1] - height);
                break;
            case ShowType.右下角:
                showContentView(anchorView, contentView,
                    location[0] + width + anchorView.getWidth(),
                    location[0] + height + anchorView.getHeight());
                break;
        }
    }

    private void dispatchLTShow() {

    }

    private void dispatchLBShow() {

    }

    private void dispatchRTShow() {

    }

    private void dispatchRBShow() {

    }

    private void showContentView(View anchorView, final View contentView, final int x,
        final int y) {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(this, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        }
        container.addView(contentView);

        contentView.post(new Runnable() {
            @Override public void run() {
                int height = contentView.getHeight();
                int width = contentView.getWidth();

                container.setX(x - width);
                container.setY(y - height);
            }
        });

        //container.setX(x);
        //container.setY(y);

        popupWindow.showAtLocation(anchorView, Gravity.TOP, 0, 0);
    }
}
