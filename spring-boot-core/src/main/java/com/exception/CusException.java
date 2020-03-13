package com.exception;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/17 16:44
 * @Description:
 */
public class CusException extends RuntimeException {


    private String msg;

    private Code code;

    public CusException(String message) {
        super(message);
        this.msg = message;
    }

    public CusException(String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
    }

    public CusException(Throwable cause) {
        super(cause);
        if (cause instanceof CusException) {
            CusException exception = (CusException) cause;
            this.msg = exception.getMsg();
        }
    }

    public String getMsg() {
        return msg;
    }

    public Code getCode() {
        return code;
    }
}

enum Code {
    ;

    Code(String msg) {
        this.msg = msg;
    }

    private String msg;

    public String getMsg() {
        return msg;
    }
}
