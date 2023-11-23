package com.example.frequencycounter.exceptions;

public class StringTooLongException extends RuntimeException{
    public StringTooLongException(String message) {
        super(message);
    }
}
