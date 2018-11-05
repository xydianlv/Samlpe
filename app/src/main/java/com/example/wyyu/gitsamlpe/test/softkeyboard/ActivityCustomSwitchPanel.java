package com.example.wyyu.gitsamlpe.test.softkeyboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.touch.OnPressListenerAdapter;
import com.example.wyyu.gitsamlpe.framework.touch.TouchListenerLayout;

/**
 * Created by wyyu on 2018/11/5.
 **/

public class ActivityCustomSwitchPanel extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityCustomSwitchPanel.class));
    }

    @BindView(R.id.switch_panel_touch) TouchListenerLayout touchListenerLayout;
    @BindView(R.id.switch_panel_publish) CustomPublishView publishView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_switch_panel);

        initActivity();
    }

    private void initActivity() {

        initToolbar("CustomSwitchPanel");

        touchListenerLayout.setOnPressListener(new OnPressListenerAdapter() {
            @Override public void onPressDown() {
                super.onPressDown();
                publishView.hideSoftKeyboard();
            }
        });

        publishView.attachToActivity(this);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (publishView != null) {
            publishView.detachFromActivity(this);
        }
    }
}
