package com.sakovich.scooterrental.api.exception;

public class OperationCancelledException extends RuntimeException {

    public OperationCancelledException(String message) {
        super(message);
    }

    public OperationCancelledException(String message, Throwable cause) {
        super(message, cause);
    }
}

