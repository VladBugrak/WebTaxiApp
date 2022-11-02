package com.taxi.controller.exceptions;

public class PastDateEditingException extends RuntimeException {
    public PastDateEditingException(){
        super();
    }
    public PastDateEditingException(String command){
        super(command);
    }
}