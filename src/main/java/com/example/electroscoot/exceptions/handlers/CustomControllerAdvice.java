package com.example.electroscoot.exceptions.handlers;

import com.example.electroscoot.exceptions.BlacklistedJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
public class CustomControllerAdvice {
    @Autowired
    private Logger logger;

    @ExceptionHandler(BlacklistedJwtException.class)
    public ResponseEntity<Object> blacklistedJwtException(BlacklistedJwtException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> expiredJwtException(ExpiredJwtException ex) {
        return new ResponseEntity<>("Your JWT Token is expired.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> signatureException(SignatureException ex) {
        return new ResponseEntity<>("Signature issues. Incorrect JWT. Try again.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Object> malformedJwtException(MalformedJwtException ex) {
        return new ResponseEntity<>("Malformed issues. Incorrect format of the JWT.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> accessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> badCredentialsException(BadCredentialsException ex, WebRequest request) {
        return new ResponseEntity<>("Authentication issues. Incorrect login or password. Try again.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> authenticationException(AuthenticationException ex, WebRequest request) {
        return new ResponseEntity<>("Authentication issues. Log in / Register, firstly.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception) {
        logger.error("IllegalArgumentException exception occurred.\nException msg: " + exception.getMessage());

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(exception.getMessage(),status);
}

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleUniqueConstraintException(DataIntegrityViolationException exception) {
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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        logger.error("MethodArgumentTypeMismatchException exception occurred. Exception msg: " + exception.getMessage());

        HttpStatus status = HttpStatus.BAD_REQUEST;

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
