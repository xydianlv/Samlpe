package com.example.wyyu.gitsamlpe.test.canvas.image;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2020-04-21.
 **/

public class ActivityCanvasImage extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityCanvasImage.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_image);
    }
}
