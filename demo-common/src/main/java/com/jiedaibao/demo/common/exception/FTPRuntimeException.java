package com.jiedaibao.demo.common.exception;

public class FTPRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -8250227621546718443L;

    public FTPRuntimeException() {
        super();
    }

    public FTPRuntimeException(String message) {
        super(message);
    }

    public FTPRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FTPRuntimeException(Throwable cause) {
        super(cause);
    }
}
