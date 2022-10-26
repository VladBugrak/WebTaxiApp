package com.taxi.controller.exceptions;

public class NonUniqGeoPointException extends RuntimeException {

    public NonUniqGeoPointException() {
        super();
    }

    public NonUniqGeoPointException(String message) {
        super(message);
    }
}
