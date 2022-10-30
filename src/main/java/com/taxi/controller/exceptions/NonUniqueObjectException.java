package com.taxi.controller.exceptions;

public class NonUniqueObjectException extends RuntimeException {

    public NonUniqueObjectException() {
        super();
    }

    public NonUniqueObjectException(String message) {
        super(message);
    }
}
