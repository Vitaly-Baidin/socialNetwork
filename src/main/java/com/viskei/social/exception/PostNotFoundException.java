package com.viskei.social.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public PostNotFoundException(String msg) {
        super(msg);
    }
}
