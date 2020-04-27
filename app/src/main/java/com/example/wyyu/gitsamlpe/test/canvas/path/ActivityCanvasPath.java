package com.example.wyyu.gitsamlpe.test.canvas.path;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2020-01-10.
 **/

public class ActivityCanvasPath extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityCanvasPath.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_path);
    }
}
