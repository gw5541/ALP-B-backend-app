package com.kt.backendapp.exception;

/**
 * 리소스 충돌 시 발생하는 예외
 */
public class ConflictException extends RuntimeException {
    
    public ConflictException(String message) {
        super(message);
    }
    
    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
