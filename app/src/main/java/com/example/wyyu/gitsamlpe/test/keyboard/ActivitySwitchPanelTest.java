package com.example.wyyu.gitsamlpe.test.keyboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import cn.dreamtobe.kpswitch.widget.KPSwitchPanelLinearLayout;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.touch.OnPressListenerAdapter;
import com.example.wyyu.gitsamlpe.framework.touch.TouchListenerLayout;

/**
 * Created by wyyu on 2018/11/1.
 **/

public class ActivitySwitchPanelTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivitySwitchPanelTest.class));
    }

    @BindView(R.id.switch_panel_root) KPSwitchPanelLinearLayout panelRoot;
    @BindView(R.id.switch_panel_touch) TouchListenerLayout listenerLayout;
    @BindView(R.id.send_msg_edit) EditText editText;
    @BindView(R.id.send_msg_icon_a) View iconA;
    @BindView(R.id.send_msg_icon_b) View iconB;
    @BindView(R.id.switch_panel_1) View panelA;
    @BindView(R.id.switch_panel_2) View panelB;
    @BindView(R.id.switch_panel_default) View defaultView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_panel_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("SwitchPanelTest");

        KeyboardUtil.attach(this, panelRoot, new KeyboardUtil.OnKeyboardShowingListener() {
            @Override public void onKeyboardShowing(boolean isShowing) {

            }
        });

        KPSwitchConflictUtilTest.attach(panelRoot, editText,
            new KPSwitchConflictUtilTest.SubPanelAndTrigger(panelA, iconA),
            new KPSwitchConflictUtilTest.SubPanelAndTrigger(panelB, iconB));

        //KPSwitchConflictUtil.attach(panelRoot, editText,
        //    new KPSwitchConflictUtil.SwitchClickListener() {
        //        @Override public void onClickSwitch(boolean switchToPanel) {
        //            if (switchToPanel) {
        //                editText.clearFocus();
        //            } else {
        //                editText.requestFocus();
        //            }
        //        }
        //    }, new KPSwitchConflictUtil.SubPanelAndTrigger(panelA, iconA),
        //    new KPSwitchConflictUtil.SubPanelAndTrigger(panelB, iconB));

        listenerLayout.setOnPressListener(new OnPressListenerAdapter() {
            @Override public void onPressDown() {
                super.onPressDown();
                KPSwitchConflictUtilTest.hidePanelAndKeyboard(panelRoot);
            }
        });
    }

    @Override public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP
            && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (panelRoot.getVisibility() == View.VISIBLE) {
                KPSwitchConflictUtilTest.hidePanelAndKeyboard(panelRoot);
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
