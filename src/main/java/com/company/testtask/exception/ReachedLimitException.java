package com.company.testtask.exception;

public class ReachedLimitException extends RuntimeException {

    public ReachedLimitException(String s) {
        super(s);
    }
}
