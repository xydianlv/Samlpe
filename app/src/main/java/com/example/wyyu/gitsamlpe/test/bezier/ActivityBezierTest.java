package com.example.wyyu.gitsamlpe.test.bezier;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;
import com.example.wyyu.gitsamlpe.test.bezier.anim.ActivityBezierAnim;
import com.example.wyyu.gitsamlpe.test.bezier.click.ActivityBezierClick;
import com.example.wyyu.gitsamlpe.test.bezier.wave.ActivityBezierWave;

/**
 * Created by wyyu on 2020-04-20.
 **/

public class ActivityBezierTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityBezierTest.class));
    }

    @BindView(R.id.bezier_test_list) ListViewMain listView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("BezierTest", 0xffffffff, 0xff84919b);

        listView.addItem("Anim", v -> ActivityBezierAnim.open(ActivityBezierTest.this))
            .addItem("Wave", v -> ActivityBezierWave.open(ActivityBezierTest.this))
            .addItem("Click", v -> ActivityBezierClick.open(ActivityBezierTest.this))
            .refreshList();
    }
}
