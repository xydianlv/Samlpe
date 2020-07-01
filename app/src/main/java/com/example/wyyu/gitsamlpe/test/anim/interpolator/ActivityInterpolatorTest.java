package com.example.wyyu.gitsamlpe.test.anim.interpolator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2020-07-01.
 **/

public class ActivityInterpolatorTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityInterpolatorTest.class));
    }

    private InterpolatorTestView[] testViewArray;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolator_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("InterpolatorTest");

        findViewById(R.id.interpolator_test_start).setOnClickListener(v -> startAnim());

        testViewArray = new InterpolatorTestView[13];

        testViewArray[0] = findViewById(R.id.interpolator_test_0);
        testViewArray[1] = findViewById(R.id.interpolator_test_1);
        testViewArray[2] = findViewById(R.id.interpolator_test_2);
        testViewArray[3] = findViewById(R.id.interpolator_test_3);
        testViewArray[4] = findViewById(R.id.interpolator_test_4);
        testViewArray[5] = findViewById(R.id.interpolator_test_5);
        testViewArray[6] = findViewById(R.id.interpolator_test_6);
        testViewArray[7] = findViewById(R.id.interpolator_test_7);
        testViewArray[8] = findViewById(R.id.interpolator_test_8);
        testViewArray[9] = findViewById(R.id.interpolator_test_9);
        testViewArray[10] = findViewById(R.id.interpolator_test_10);
        testViewArray[11] = findViewById(R.id.interpolator_test_11);
        testViewArray[12] = findViewById(R.id.interpolator_test_12);
    }

    private void startAnim() {
        if (testViewArray == null || testViewArray.length <= 0) {
            return;
        }
        for (InterpolatorTestView testView : testViewArray) {
            testView.startAnim();
        }
    }
}
