package com.example.wyyu.gitsamlpe.test.nine.util;

/**
 * Created by wyyu on 2019-08-07.
 **/
public class WrongPaddingException extends RuntimeException {
    public WrongPaddingException() {
    }

    public WrongPaddingException(String detailMessage) {
        super(detailMessage);
    }

    public WrongPaddingException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public WrongPaddingException(Throwable throwable) {
        super(throwable);
    }
}
