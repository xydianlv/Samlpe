package com.example.wyyu.gitsamlpe.test.function.nine.util;

/**
 * Created by wyyu on 2019-08-07.
 **/

public class DivLengthException extends RuntimeException {
    public DivLengthException() {
    }

    public DivLengthException(String detailMessage) {
        super(detailMessage);
    }

    public DivLengthException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DivLengthException(Throwable throwable) {
        super(throwable);
    }
}
