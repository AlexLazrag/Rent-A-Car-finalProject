package com.finalproject.rentacar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserNotFoundException(NotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleDublicateEntry(SQLIntegrityConstraintViolationException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        int index = ex.getMessage().indexOf("for");
        errorMessage.setMessage(ex.getMessage().substring(0, index - 1));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException ex){

        MethodArgumentNotValidException exception = ex;

        Map<String,String> fieldsErrors = new HashMap<>();
        for(FieldError error: ex.getFieldErrors()){
            fieldsErrors.put(error.getField(),error.getDefaultMessage());
        }
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage("There are some violations");
        errorMessage.setFieldsViolated(fieldsErrors);

        return ResponseEntity.badRequest().body(errorMessage);
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ErrorMessage> handleDuplicateEmailException(DuplicateEntityException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(ex.getErrorMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

}
