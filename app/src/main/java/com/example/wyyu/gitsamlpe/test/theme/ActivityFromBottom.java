package com.example.wyyu.gitsamlpe.test.theme;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.BaseActivity;
import com.example.wyyu.gitsamlpe.util.WindowUtils;

/**
 * Created by wyyu on 2019/2/22.
 **/

public class ActivityFromBottom extends BaseActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            overridePendingTransition(R.anim.slide_bottom_enter, 0);
        }
        setContentView(R.layout.activity_from_bottom);
        installSwipeLayout();
    }

    @Override public void finish() {
        super.finish();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            overridePendingTransition(0, R.anim.slide_bottom_exit);
        }
    }

    private void installSwipeLayout() {
        WindowUtils.convertActivityToTranslucent(this);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
}
