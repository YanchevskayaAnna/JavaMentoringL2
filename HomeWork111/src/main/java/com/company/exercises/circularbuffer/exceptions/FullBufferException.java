package com.company.exercises.circularbuffer.exceptions;

public class FullBufferException extends RuntimeException {
    public FullBufferException(String message) {
        super(message);
    }
}
