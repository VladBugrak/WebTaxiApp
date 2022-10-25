package com.taxi.controller.exceptions;

public class NotUniqUserException extends RuntimeException {

    public NotUniqUserException() {
        super();
    }

    public NotUniqUserException(String message) {
        super(message);
    }
}
