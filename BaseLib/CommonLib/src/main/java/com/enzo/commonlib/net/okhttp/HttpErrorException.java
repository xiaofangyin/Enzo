package com.enzo.commonlib.net.okhttp;

public class HttpErrorException extends Exception {

    /**
     * 网络请求返回的error code
     */
    private int code;

    /**
     * 网络请求返回的result
     */
    private String result;

    public HttpErrorException(int code, String detailMessage) {
        super(detailMessage);
        this.code = code;
    }

    public HttpErrorException(int code) {
        this(code, "");
    }

    public HttpErrorException(String result, String detailMessage) {
        super(detailMessage);
        this.result = result;
    }

    public HttpErrorException(String result) {
        this(result, "");
    }

    public int getCode() {
        return code;
    }

    public String getResult() {
        return result;
    }
}
