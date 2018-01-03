package com.example.wyyu.gitsamlpe.test.slide;

import android.os.Bundle;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2018/1/2.
 **/

public class ActivitySlide extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        initActivitySlide();
    }

    private void initActivitySlide() {

        initToolbar("1", 0xffe6e6e6, 0xff84919b);
    }
}
