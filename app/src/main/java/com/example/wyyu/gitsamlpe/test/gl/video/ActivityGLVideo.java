package com.example.wyyu.gitsamlpe.test.gl.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2021/3/10.
 **/

public class ActivityGLVideo extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityGLVideo.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
