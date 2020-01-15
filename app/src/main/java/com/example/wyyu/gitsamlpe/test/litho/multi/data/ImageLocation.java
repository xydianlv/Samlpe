package com.example.wyyu.gitsamlpe.test.litho.multi.data;

import java.io.Serializable;

/**
 * Created by wyyu on 2020-01-14.
 **/

public class ImageLocation implements Serializable {

    public float left;
    public float top;
    public float width;
    public float height;

    public ImageLocation() {

    }

    public ImageLocation(float left, float top, float width, float height) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }
}
