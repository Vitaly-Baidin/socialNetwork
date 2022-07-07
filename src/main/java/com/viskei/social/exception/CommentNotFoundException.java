package com.viskei.social.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CommentNotFoundException(String msg) {
        super(msg);
    }
}
