package com.example.wyyu.gitsamlpe.test.bezier.click;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2020-03-22.
 **/

public class ActivityBezierClick extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityBezierClick.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_click);

        initActivity();
    }

    private void initActivity() {

    }
}
