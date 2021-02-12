package com.clonnit.demo.exceptions;

public class ClonnitException extends RuntimeException {
    public ClonnitException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public ClonnitException(String exMessage) {
        super(exMessage);
    }
}
