package com.example.wyyu.gitsamlpe.test.theme;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.util.WindowUtils;

/**
 * Created by wyyu on 2019/3/4.
 **/

public class ActivitySwipeRight extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivitySwipeRight.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        //if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //    View decorView = window.getDecorView();
        //    int sysUIFlag = decorView.getSystemUiVisibility();
        //    sysUIFlag |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        //    decorView.setSystemUiVisibility(sysUIFlag);
        //}

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_right);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            overridePendingTransition(R.anim.activity_right_enter, 0);
        }

        WindowUtils.convertActivityToTranslucent(this);
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        initActivity();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }

    @Override public void finish() {
        super.finish();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        overridePendingTransition(0, R.anim.activity_right_exit);
    }

    private void initActivity() {

        initToolbar("SwipeTest");
    }
}
