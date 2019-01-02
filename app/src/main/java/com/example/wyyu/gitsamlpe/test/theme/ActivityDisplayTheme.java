package com.example.wyyu.gitsamlpe.test.theme;

import android.app.Activity;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/4/12.
 * 一个不在屏幕中显示出来的 activity，必须在 onResume 中 finish 掉
 * 但是它的生命周期依然正常存在
 **/

public class ActivityDisplayTheme extends Activity{

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_theme);
    }

    @Override protected void onResume() {
        super.onResume();
        finish();
    }
}
