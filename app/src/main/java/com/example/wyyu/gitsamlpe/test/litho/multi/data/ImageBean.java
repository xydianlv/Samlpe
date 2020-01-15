package com.example.wyyu.gitsamlpe.test.litho.multi.data;

import java.io.Serializable;

/**
 * Created by wyyu on 2019-09-27.
 **/

public class ImageBean implements Serializable {

    public int resId;
    public int width;
    public int height;

    public ImageBean(int resId, int width, int height) {
        this.resId = resId;
        this.width = width;
        this.height = height;
    }
}
