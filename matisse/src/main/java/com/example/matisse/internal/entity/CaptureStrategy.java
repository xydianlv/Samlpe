package com.example.matisse.internal.entity;

/**
 * Created by wyyu on 2019/4/2.
 **/
public class CaptureStrategy {

    public final boolean isPublic;
    public final String authority;

    public CaptureStrategy(boolean isPublic, String authority) {
        this.isPublic = isPublic;
        this.authority = authority;
    }
}
