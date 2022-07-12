package com.example.wyyu.gitsamlpe.test.function.shortcut;

import android.os.Bundle;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

public class ActivityShotCutPreview extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_cut_preview);

        initActivity();
    }

    private void initActivity() {
        initToolbar("ShotCutPreview", 0xffffffff, 0xff84919b);
    }
}
