package com.task.test.sasha.exception;

public class NoMatchesException extends RuntimeException{
    public NoMatchesException() {
    }

    public NoMatchesException(String message) {
        super(message);
    }
}
