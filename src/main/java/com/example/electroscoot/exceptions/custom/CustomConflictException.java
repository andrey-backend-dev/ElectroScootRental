package com.example.electroscoot.exceptions.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class CustomConflictException extends RuntimeException {

    public CustomConflictException(String msg) {
        super(msg);
    }
}
