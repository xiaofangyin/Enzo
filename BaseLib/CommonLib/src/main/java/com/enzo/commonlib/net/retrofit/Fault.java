package com.enzo.commonlib.net.retrofit;

public class Fault extends RuntimeException {

    private int errorCode;

    public Fault(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
