package com.example.exoonepay2.exception;

public class AppException extends Exception{

    /**
     * This exception is used in case of a status error
     * @param msg
     */
    public AppException(String msg) {
        super(msg);
    }
}
