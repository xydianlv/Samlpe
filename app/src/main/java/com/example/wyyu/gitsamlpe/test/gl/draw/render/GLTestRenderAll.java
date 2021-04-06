package com.example.wyyu.gitsamlpe.test.gl.draw.render;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by wyyu on 2021/3/10.
 **/

public class GLTestRenderAll implements GLSurfaceView.Renderer {

    private Triangle triangle;

    @Override public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        triangle = new Triangle();
    }

    @Override public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }

    @Override public void onDrawFrame(GL10 gl) {
        // 重绘背景色
        GLES30.glClear(GL10.GL_COLOR_BUFFER_BIT);
        // 设置 Frame 背景色
        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        if (triangle != null) {
            triangle.draw();
        }
    }
}
