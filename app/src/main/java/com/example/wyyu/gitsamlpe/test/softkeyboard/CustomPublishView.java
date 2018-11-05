package com.example.wyyu.gitsamlpe.test.softkeyboard;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import cn.dreamtobe.kpswitch.widget.KPSwitchPanelLinearLayout;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/11/5.
 **/

public class CustomPublishView extends LinearLayout implements IPublishAttach {

    public CustomPublishView(Context context) {
        this(context, null);
    }

    public CustomPublishView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomPublishView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPublishView();
    }

    private KPSwitchPanelLinearLayout switchPanelLinearLayout;
    private EditText editText;
    private View panelA;
    private View panelB;
    private View iconA;
    private View iconB;

    private void initPublishView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_publish_view, this);

        switchPanelLinearLayout = findViewById(R.id.custom_publish_panel_root);
        editText = findViewById(R.id.custom_publish_edit);
        panelA = findViewById(R.id.custom_publish_panel_1);
        panelB = findViewById(R.id.custom_publish_panel_2);
        iconA = findViewById(R.id.custom_publish_icon_a);
        iconB = findViewById(R.id.custom_publish_icon_b);
    }

    @Override public void detachFromActivity(@NonNull Activity activity) {
        KeyboardUtil.detach(activity, new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override public void onGlobalLayout() {

            }
        });
    }

    @Override public void attachToActivity(@NonNull Activity activity) {

        KPSwitchConflictUtilTest.attach(switchPanelLinearLayout, editText,
            new KPSwitchConflictUtilTest.SubPanelAndTrigger(panelA, iconA),
            new KPSwitchConflictUtilTest.SubPanelAndTrigger(panelB, iconB));

        KeyboardUtil.attach(activity, switchPanelLinearLayout);
    }

    public void hideSoftKeyboard() {
        if (switchPanelLinearLayout == null) {
            return;
        }
        KPSwitchConflictUtilTest.hidePanelAndKeyboard(switchPanelLinearLayout);
    }
}
