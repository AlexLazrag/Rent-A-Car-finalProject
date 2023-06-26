package com.finalproject.rentacar.exceptions;

public class DuplicateEntityException extends RuntimeException{
    private final String errorMessage;

    public DuplicateEntityException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
