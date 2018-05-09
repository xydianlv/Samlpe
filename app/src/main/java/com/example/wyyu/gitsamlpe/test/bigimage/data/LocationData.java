package com.example.wyyu.gitsamlpe.test.bigimage.data;

import java.io.Serializable;

/**
 * Created by wyyu on 2018/5/8.
 **/

public class LocationData implements Serializable {

    public int bottom;
    public int right;
    public int left;
    public int top;

    public LocationData(int left, int top, int right, int bottom) {

        this.bottom = bottom;
        this.right = right;
        this.left = left;
        this.top = top;
    }
}
