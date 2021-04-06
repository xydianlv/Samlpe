package com.example.wyyu.gitsamlpe.test.gl.draw.util;

import android.opengl.GLES30;

/**
 * Created by wyyu on 2021/3/10.
 **/

public class GLUtils {

    /**
     * 编译一段 GLSL 代码生成 Shader
     *
     * @param type Fragment型 /
     * @param shaderCode GLSL代码
     * @return Render
     */
    public static int loadShader(int type, String shaderCode) {

        // 根据 type 创建一个 VertexShader 或 FragmentShader
        int shader = GLES30.glCreateShader(type);

        // 添加 shaderCode 到 shader 并执行编译
        GLES30.glShaderSource(shader, shaderCode);
        GLES30.glCompileShader(shader);

        return shader;
    }
}
