package com.example.wyyu.gitsamlpe.test.animation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;

/**
 * Created by wyyu on 2020-02-08.
 **/

public class ActivityLinearAnim extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityLinearAnim.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_anim);

        initActivity();
    }

    private void initActivity() {
        initToolbar("LinearAnim", 0xffffffff, 0xff84919b);

        ListViewMain animList = findViewById(R.id.linear_anim_list);

        animList.addItem("SpringAnim", v -> ActivitySpringAnimation.open(ActivityLinearAnim.this))
            .addItem("AnimWrapHeight", v -> ActivityAnimWrapHeight.open(ActivityLinearAnim.this))
            .refreshList();
    }
}
