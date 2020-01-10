package com.example.wyyu.gitsamlpe.test.canvas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2020-01-09.
 **/

public class ActivityCanvasText extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityCanvasText.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_text);

        initToolbar("CanvasText", 0xffffffff, 0xff84919b);
    }
}
