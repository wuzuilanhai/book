package com.biubiu.exception;

/**
 * Created by Haibiao.Zhang on 2019-03-27 13:55
 */
public class BookException extends RuntimeException {

    public BookException() {
        super();
    }

    public BookException(String message) {
        super(message);
    }

    public BookException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookException(Throwable cause) {
        super(cause);
    }

    protected BookException(String message, Throwable cause,
                            boolean enableSuppression,
                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
