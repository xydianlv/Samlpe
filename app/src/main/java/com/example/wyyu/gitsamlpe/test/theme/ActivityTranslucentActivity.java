package com.example.wyyu.gitsamlpe.test.theme;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/4/12.
 * 一个透明的 Activity，相当于在前一个 activity 上面悬浮显示自己拥有的 控件
 **/

public class ActivityTranslucentActivity extends Activity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translucent_theme);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_left_to_mid, R.anim.from_mid_to_right);
    }
}
