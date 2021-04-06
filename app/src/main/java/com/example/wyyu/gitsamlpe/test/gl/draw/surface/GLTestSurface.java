package com.example.wyyu.gitsamlpe.test.gl.draw.surface;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import com.example.wyyu.gitsamlpe.test.gl.draw.render.GLTestRender;

/**
 * Created by wyyu on 2021/3/9.
 **/

public class GLTestSurface extends GLSurfaceView {

    public GLTestSurface(Context context) {
        super(context);
        initGLView();
    }

    public GLTestSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGLView();
    }

    private void initGLView() {
        setEGLContextClientVersion(3);

        GLTestRender testRender = new GLTestRender();
        setRenderer(testRender);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
