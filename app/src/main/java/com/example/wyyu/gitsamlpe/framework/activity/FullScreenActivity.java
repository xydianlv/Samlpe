package com.example.wyyu.gitsamlpe.framework.activity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class FullScreenActivity extends BaseActivity {

    private static final int THEME = R.style.FullScreenTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(THEME);
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}