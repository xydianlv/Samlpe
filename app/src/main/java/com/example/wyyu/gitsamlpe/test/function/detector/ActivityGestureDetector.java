package com.example.wyyu.gitsamlpe.test.function.detector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.BaseActivity;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;

/**
 * Created by wyyu on 2019/2/26.
 **/

public class ActivityGestureDetector extends BaseActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityGestureDetector.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_detector_test);

        initActivity();
    }

    private void initActivity() {
        DetectorTestView testView = findViewById(R.id.detector_test);

        testView.setDetectorTapListener(new DetectorTestView.OnDetectorTapListener() {
            @Override public boolean onPressDown() {
                return false;
            }

            @Override public boolean onDoubleClick() {
                return false;
            }

            @Override public boolean onLongClick() {
                return false;
            }
        });

        findViewById(R.id.detector_test_green).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                UToast.showShort(ActivityGestureDetector.this, "green");
            }
        });

        findViewById(R.id.detector_test_red).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                UToast.showShort(ActivityGestureDetector.this, "red");
            }
        });
    }
}
