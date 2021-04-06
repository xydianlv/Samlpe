package com.example.wyyu.gitsamlpe.test.gl.draw;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.FrameLayout;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.gl.draw.render.GLTestRenderAll;
import com.example.wyyu.gitsamlpe.test.gl.draw.surface.GLTestSurface;

/**
 * Created by wyyu on 2021/3/10.
 **/

public class ActivityGLDraw extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityGLDraw.class));
    }

    @BindView(R.id.gl_draw_container) FrameLayout container;
    @BindView(R.id.gl_draw_container_all) FrameLayout containerAll;

    private GLTestSurface surface;
    private GLSurfaceView surfaceView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gl_draw);

        initActivity();
    }

    @Override protected void onResume() {
        super.onResume();
        if (surface != null) {
            surface.onResume();
        }
        if (surfaceView != null) {
            surfaceView.onResume();
        }
    }

    @Override protected void onPause() {
        super.onPause();
        if (surface != null) {
            surface.onPause();
        }
        if (surfaceView != null) {
            surfaceView.onPause();
        }
    }

    private void initActivity() {
        initToolbar("GLDraw", 0xffffffff, 0xff84919b);

        surface = new GLTestSurface(this);
        container.addView(surface);

        surfaceView = new GLSurfaceView(this);
        surfaceView.setEGLContextClientVersion(3);

        GLTestRenderAll renderAll = new GLTestRenderAll();
        surfaceView.setRenderer(renderAll);
        surfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        containerAll.addView(surfaceView);
    }
}
