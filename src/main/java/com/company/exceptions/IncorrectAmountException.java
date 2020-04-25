package com.company.exceptions;

public class IncorrectAmountException extends RuntimeException {
    public IncorrectAmountException(String message) {
        super(message);
    }
}
