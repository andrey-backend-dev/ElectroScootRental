package com.example.electroscoot.controllers;

import com.example.electroscoot.exceptions.custom.CustomConflictException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
public class CustomControllerAdvice {
    @Autowired
    private Logger logger;

    @ExceptionHandler(CustomConflictException.class)
    public ResponseEntity<Object> handleCustomConflictException(Exception exception) {
        logger.error("CustomConflictException exception occurred.\nException msg: " + exception.getMessage());

        HttpStatus status = HttpStatus.CONFLICT;

        return new ResponseEntity<>(exception.getMessage(), status);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(Exception exception) {
        logger.error("IllegalArgumentException exception occurred.\nException msg: " + exception.getMessage());

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(exception.getMessage(),status);
}

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleUniqueConstraintException(Exception exception) {
        String customMsg = "The field <" +
                exception.getMessage().split("'")[3].split("\\.")[1].split("_")[0] +
                "> is unique. The value you've wrote (" +
                exception.getMessage().split("'")[1] +
                ") is already reserved. Try another one.";

        logger.error("SQLIntegrityConstraintViolationException occurred (unique constraint).\nException msg: " + customMsg);

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(customMsg, status);
    }

    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationException(Exception exception) {
        logger.error("Validation exception occurred. Exception msg: " + exception.getMessage());

        HttpStatus status = HttpStatus.BAD_REQUEST;

        // if exception message format is "SomeException.class: message"
        if (exception.getMessage().split(": ").length > 1) {
            return new ResponseEntity<>(exception.getMessage().split(": ")[1], status);
        }

        return new ResponseEntity<>(exception.getMessage(), status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherExceptions(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        logger.error("Exception occurred.\nException msg: " + exception.getMessage());
        logger.debug("Stack Trace: " + stackTrace);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(exception.getMessage(), status);
    }
}
