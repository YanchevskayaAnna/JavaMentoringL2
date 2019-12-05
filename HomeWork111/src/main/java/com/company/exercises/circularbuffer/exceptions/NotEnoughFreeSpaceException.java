package com.company.exercises.circularbuffer.exceptions;

public class NotEnoughFreeSpaceException extends RuntimeException {
    public NotEnoughFreeSpaceException(String message) {
        super(message);
    }
}
