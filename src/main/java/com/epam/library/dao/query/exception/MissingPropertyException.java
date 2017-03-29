package com.epam.library.dao.query.exception;

/**
 * Created by Yauhen_Yushkevich on 29.03.17.
 */
public class MissingPropertyException extends Exception {

    public MissingPropertyException() {
        super();
    }

    public MissingPropertyException(String message) {
        super(message);
    }

    public MissingPropertyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingPropertyException(Throwable cause) {
        super(cause);
    }

    protected MissingPropertyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
