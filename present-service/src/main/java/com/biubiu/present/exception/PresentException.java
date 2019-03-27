package com.biubiu.present.exception;

/**
 * Created by Haibiao.Zhang on 2019-03-27 13:55
 */
public class PresentException extends RuntimeException {

    public PresentException() {
        super();
    }

    public PresentException(String message) {
        super(message);
    }

    public PresentException(String message, Throwable cause) {
        super(message, cause);
    }

    public PresentException(Throwable cause) {
        super(cause);
    }

    protected PresentException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
