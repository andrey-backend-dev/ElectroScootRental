package com.example.electroscoot.exceptions;

import lombok.Getter;

@Getter
public class CustomConflictException extends RuntimeException {

    public CustomConflictException(String msg) {
        super(msg);
    }
}
