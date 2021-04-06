package com.example.wyyu.gitsamlpe.test.gl.draw.render;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by wyyu on 2021/3/9.
 *
 * 单精度型浮点数，一个浮点数占用四个字节
 **/

public class GLTestRender implements GLSurfaceView.Renderer {

    // 确定一个顶点需要的坐标个数
    private static final int COORDS_PRE_VERTEX = 3;
    // 坐标列表，浮点数指在 SurfaceView 中的百分比位置
    private static final float[] COORDS = new float[] {
        0.0f, 0.5f, 0.0f,   // TOP
        -0.5f, -0.5f, 0.0f, // BOTTOM_LEFT
        0.5f, -0.5f, 0.0f   // BOTTOM_RIGHT
    };

    // 绘制三角形顶点的 GLSL 代码
    private static final String VERTEX_SHADER_CODE = "uniform mat4 uMVPMatrix;"
        + "attribute vec4 vPosition;"
        + "void main() {"
        + "  gl_Position = uMVPMatrix * vPosition;"
        + "}";

    // 渲染三角形颜色的 GLSL 代码
    private static final String FRAGMENT_SHADER_CODE = "precision mediump float;"
        + "uniform vec4 vColor;"
        + "void main() {"
        + "  gl_FragColor = vColor;"
        + "}";

    // 色值数组 RGBA
    private static final float[] COLOR = { 1.0f, 0.0f, 0.0f, 1.0f };

    // 顶点个数
    private static final int VERTEX_COUNT = COORDS.length / COORDS_PRE_VERTEX;
    // 描述一个顶点需要的字节数，4 Bytes Per Vertex
    private static final int VERTEX_STRIDE = COORDS_PRE_VERTEX * 4;

    // 存放坐标的浮点数组
    private FloatBuffer buffer;
    //
    private int program;

    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    @Override public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 调用一次，设置视图的 OpenGL ES 环境

        // 为坐标数组初始化一个 ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(COORDS.length * 4);
        // 采用硬件加速的 ByteOrder
        byteBuffer.order(ByteOrder.nativeOrder());

        // 由 ByteBuffer 生成一个 浮点数组
        buffer = byteBuffer.asFloatBuffer();
        // 设置坐标数据
        buffer.put(COORDS);
        // 设置读取 COORDS 数组的第一个 数组下标
        buffer.position(0);

        // 生成一个顶点 shader
        int vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, VERTEX_SHADER_CODE);
        // 生成一个渲染 shader
        int fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, FRAGMENT_SHADER_CODE);

        // 创建一个空的 OpenGLESProgram
        program = GLES30.glCreateProgram();

        // 绑定 VertexShader 到 Program
        GLES30.glAttachShader(program, vertexShader);
        // 绑定 FragmentShader 到 Program
        GLES30.glAttachShader(program, fragmentShader);

        // 创建一个该 Program 的可执行文件
        GLES30.glLinkProgram(program);
    }

    @Override public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 当视图的几何图形发生变化时调用

        GLES30.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

        //Matrix.frustumM(
        //float[] m,    // 接收透视投影的变换矩阵
        //int offset,   // 变换矩阵的起始位置（偏移量）
        //float left,   // 相对观察点近面的左边距
        //float right,  // 相对观察点近面的右边距
        //float bottom, // 相对观察点近面的下边距
        //float top,    // 相对观察点近面的上边距
        //float near,   // 相对观察点近面距离
        //float far     // 相对观察点远面距离
        //)
    }

    @Override public void onDrawFrame(GL10 gl) {

        // 重绘背景色
        GLES30.glClear(GL10.GL_COLOR_BUFFER_BIT);
        // 设置 Frame 背景色
        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //Matrix.setLookAtM(
        //float[] rm,                                   // 接收相机变换矩阵
        //int rmOffset,                                 // 变换矩阵的起始位置（偏移量）
        //float eyeX, float eyeY, float eyeZ,           // 相机位置
        //float centerX, float centerY, float centerZ,  // 观察点位置
        //float upX, float upY, float upZ               // up向量在xyz上的分量
        //)

        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        //Matrix.multiplyMM(
        //float[] result,     // 接收相乘结果
        //int resultOffset,   // 接收矩阵的起始位置（偏移量）
        //float[] lhs,        // 左矩阵
        //int lhsOffset,      // 左矩阵的起始位置（偏移量）
        //float[] rhs,        // 右矩阵
        //int rhsOffset       // 右矩阵的起始位置（偏移量）
        //)


        // 添加 OpenGLESProgram 到 OpenGLES 运行环境
        GLES30.glUseProgram(program);

        // get handle to vertex shader's vPosition member 获取 VertexShader 的 vPosition 成员句柄
        int positionHandle = GLES30.glGetAttribLocation(program, "vPosition");
        // Enable a handle to the triangle vertices 启用三角形顶点的句柄
        GLES30.glEnableVertexAttribArray(positionHandle);
        // Prepare the triangle coordinate data 准备三角形坐标数据
        GLES30.glVertexAttribPointer(positionHandle, COORDS_PRE_VERTEX, GLES30.GL_FLOAT, false,
            VERTEX_STRIDE, buffer);
        // get handle to fragment shader's vColor member 获取 ProgramShader 的 vColor 成员句柄
        int colorHandle = GLES30.glGetUniformLocation(program, "vColor");
        // Set color for drawing the triangle 为三角形设置颜色
        GLES30.glUniform4fv(colorHandle, 1, COLOR, 0);

        // get handle to shape's transformation matrix
        int vPMatrixHandle = GLES30.glGetUniformLocation(program, "vMatrix");
        // Pass the projection and view transformation to the shader
        GLES30.glUniformMatrix4fv(vPMatrixHandle, 1, false, vPMatrix, 0);

        // Draw the triangle 绘制三角形
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, VERTEX_COUNT);
        // Disable vertex array 禁止顶点句柄
        GLES30.glDisableVertexAttribArray(positionHandle);
    }

    private int loadShader(int type, String shaderCode) {

        // 根据 type 创建一个 VertexShader 或 FragmentShader
        int shader = GLES30.glCreateShader(type);

        // 添加 shaderCode 到 shader 并执行编译
        GLES30.glShaderSource(shader, shaderCode);
        GLES30.glCompileShader(shader);

        return shader;
    }
}