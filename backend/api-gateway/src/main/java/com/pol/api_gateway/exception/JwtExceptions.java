package com.pol.api_gateway.exception;

public class JwtExceptions extends RuntimeException {
    public JwtExceptions(String message) {
        super(message);
    }
}
