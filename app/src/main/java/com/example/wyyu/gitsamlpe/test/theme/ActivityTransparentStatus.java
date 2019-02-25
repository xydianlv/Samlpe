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
import com.example.wyyu.gitsamlpe.framework.activity.BaseActivity;
import com.example.wyyu.gitsamlpe.util.WindowUtils;

/**
 * Created by wyyu on 2019/2/22.
 **/

public class ActivityTransparentStatus extends BaseActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityTransparentStatus.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {

        if(enableStatusBarColor()){
            if (isFullScreen()) {
                setFullScreenFlag(getWindow());
            }
        }
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            overridePendingTransition(R.anim.slide_bottom_enter, 0);
        }
        setContentView(R.layout.activity_transparent_status);

        WindowUtils.convertActivityToTranslucent(this);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        float statusHeight = getResources().getDimension(R.dimen.status_bar_height);
        View view = findViewById(R.id.status_title);
        view.setPadding(0, (int) statusHeight, 0, 0);
    }

    protected boolean enableStatusBarColor(){
        return true;
    }

    protected boolean isFullScreen() {
        return true;
    }

    private void setFullScreenFlag(Window window) {
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = window.getDecorView();
            int sysUIFlag = decorView.getSystemUiVisibility();
            sysUIFlag |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(sysUIFlag);
        }
    }
}
