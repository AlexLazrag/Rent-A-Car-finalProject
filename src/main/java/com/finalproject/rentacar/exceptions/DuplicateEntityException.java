package com.finalproject.rentacar.exceptions;

public class DuplicateEntityException extends RuntimeException{
    private final String errorMessage;

    // Добавено с цел при запазване на резервация със съществуващ userId  да не се инкрементира ID на резервацията без да се запазва в базата данни
    //Същото се отнася за userId При създаванер на user

    public DuplicateEntityException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
