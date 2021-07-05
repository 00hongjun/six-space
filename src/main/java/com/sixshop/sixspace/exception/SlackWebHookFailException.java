package com.sixshop.sixspace.exception;

public class SlackWebHookFailException extends RuntimeException {

    public SlackWebHookFailException(final String message) {
        super(message);
    }
}
