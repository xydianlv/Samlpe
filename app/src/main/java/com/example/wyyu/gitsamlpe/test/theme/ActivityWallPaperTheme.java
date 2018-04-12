package com.example.wyyu.gitsamlpe.test.theme;

import android.app.Activity;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/4/12.
 * 自带手机 壁纸背景 的 Activity
 **/

public class ActivityWallPaperTheme extends Activity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_paper_theme);
    }

    @Override public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_left_to_mid, R.anim.from_mid_to_right);
    }
}
