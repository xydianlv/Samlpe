package com.example.wyyu.gitsamlpe.test.theme;

import android.app.Activity;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/4/12.
 * 一个会话框样式的 activity，大小取决于 布局文件 大小，点击旁边区域会退出
 **/

public class ActivityDialogTheme extends Activity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_theme);
    }

    @Override public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_left_to_mid, R.anim.from_mid_to_right);
    }
}
