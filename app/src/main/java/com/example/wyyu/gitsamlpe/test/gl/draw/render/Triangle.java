package com.example.wyyu.gitsamlpe.test.gl.draw.render;

import android.opengl.GLES30;
import com.example.wyyu.gitsamlpe.test.gl.draw.util.GLUtils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by wyyu on 2021/3/10.
 **/

public class Triangle {

    // 确定一个顶点需要的坐标个数
    private static final int COORDS_PRE_VERTEX = 3;
    // 坐标列表，浮点数指在 SurfaceView 中的百分比位置
    private static final float[] COORDS = new float[] {
        0.0f, 0.5f, 0.0f,   // TOP
        -0.5f, -0.5f, 0.0f, // BOTTOM_LEFT
        0.5f, -0.5f, 0.0f   // BOTTOM_RIGHT
    };

    // 绘制三角形顶点的 GLSL 代码
    private static final String VERTEX_SHADER_CODE =
        "attribute vec4 vPosition;" + "void main() {" + "  gl_Position = vPosition;" + "}";

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

    public Triangle() {

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
        int vertexShader = GLUtils.loadShader(GLES30.GL_VERTEX_SHADER, VERTEX_SHADER_CODE);
        // 生成一个渲染 shader
        int fragmentShader = GLUtils.loadShader(GLES30.GL_FRAGMENT_SHADER, FRAGMENT_SHADER_CODE);

        // 创建一个空的 OpenGLESProgram
        program = GLES30.glCreateProgram();

        // 绑定 VertexShader 到 Program
        GLES30.glAttachShader(program, vertexShader);
        // 绑定 FragmentShader 到 Program
        GLES30.glAttachShader(program, fragmentShader);

        // 创建一个该 Program 的可执行文件
        GLES30.glLinkProgram(program);
    }

    public void draw() {

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
        // Draw the triangle 绘制三角形
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, VERTEX_COUNT);
        // Disable vertex array 禁止顶点句柄
        GLES30.glDisableVertexAttribArray(positionHandle);
    }
}
