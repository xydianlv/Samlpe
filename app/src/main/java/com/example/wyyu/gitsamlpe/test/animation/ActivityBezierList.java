package com.example.wyyu.gitsamlpe.test.animation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.animation.click.ActivityBezierDoubleClick;

/**
 * Created by wyyu on 2019-11-27.
 **/

public class ActivityBezierList extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityBezierList.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_list);

        initActivity();
    }

    private void initActivity() {
        initToolbar("BezierList", 0xffffffff, 0xff84919b);

        findViewById(R.id.bezier_list_click).setOnClickListener(
            v -> ActivityBezier.open(ActivityBezierList.this));

        findViewById(R.id.bezier_list_anim).setOnClickListener(
            v -> startActivity(new Intent(ActivityBezierList.this, ActivityBezierAnim.class)));

        findViewById(R.id.bezier_list_double_click).setOnClickListener(v -> startActivity(
            new Intent(ActivityBezierList.this, ActivityBezierDoubleClick.class)));
    }
}
