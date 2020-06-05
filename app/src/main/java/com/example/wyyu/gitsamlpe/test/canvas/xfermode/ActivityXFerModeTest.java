package com.example.wyyu.gitsamlpe.test.canvas.xfermode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2020-06-05.
 **/

public class ActivityXFerModeTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityXFerModeTest.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_fer_mode_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("XFerModeTest");
    }
}
