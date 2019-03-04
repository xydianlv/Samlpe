package com.example.wyyu.gitsamlpe.test.visibility;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2019/3/4.
 **/

public class ActivityUiVisibilityTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityUiVisibilityTest.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_visibility_test);

        if (Build.VERSION.SDK_INT >= ActivitySystemUiVisibility.BUILD_VERSION) {
            getWindow().getDecorView()
                .setSystemUiVisibility(ActivitySystemUiVisibility.SYSTEM_UI_VISIBILITY);
        }

        initActivity();
    }

    private void initActivity() {
        initToolbar(String.valueOf(ActivitySystemUiVisibility.SYSTEM_UI_VISIBILITY));
    }
}
