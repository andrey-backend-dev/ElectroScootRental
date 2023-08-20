package com.example.electroscoot.exceptions;

import io.jsonwebtoken.JwtException;

public class BlacklistedJwtException extends JwtException {

    public BlacklistedJwtException(String message) {
        super(message);
    }

    public BlacklistedJwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
