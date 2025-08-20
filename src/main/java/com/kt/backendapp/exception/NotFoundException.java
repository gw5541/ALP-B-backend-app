package com.kt.backendapp.exception;

/**
 * 리소스를 찾을 수 없을 때 발생하는 예외
 */
public class NotFoundException extends RuntimeException {
    
    public NotFoundException(String message) {
        super(message);
    }
    
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
