package com.example.wyyu.gitsamlpe.test.theme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2019/5/29.
 **/

public class ActivityResultTest extends FullScreenActivity {

    public static void openForResult(Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, ActivityResultTest.class),
            requestCode);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_test);
    }
}
