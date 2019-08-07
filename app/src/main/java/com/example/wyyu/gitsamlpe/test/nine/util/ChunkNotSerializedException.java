package com.example.wyyu.gitsamlpe.test.nine.util;

/**
 * Created by wyyu on 2019-08-07.
 **/
public class ChunkNotSerializedException extends RuntimeException{

    public ChunkNotSerializedException() {
    }

    public ChunkNotSerializedException(String detailMessage) {
        super(detailMessage);
    }

    public ChunkNotSerializedException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ChunkNotSerializedException(Throwable throwable) {
        super(throwable);
    }
}
