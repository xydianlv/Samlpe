package com.example.wyyu.gitsamlpe.test.theme;

import android.app.Activity;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/4/12.
 * 类似于 dialogTheme，大小取决于布局文件，且界面更纯粹，点击旁边区域不会退出
 **/

public class ActivityPanelTheme extends Activity{

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_theme);
    }

    @Override public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_left_to_mid, R.anim.from_mid_to_right);
    }
}
