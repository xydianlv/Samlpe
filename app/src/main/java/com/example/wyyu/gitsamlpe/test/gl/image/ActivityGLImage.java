package com.example.wyyu.gitsamlpe.test.gl.image;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2021/3/9.
 **/

public class ActivityGLImage extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityGLImage.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
