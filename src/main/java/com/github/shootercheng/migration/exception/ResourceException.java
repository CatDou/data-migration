package com.github.shootercheng.migration.exception;

/**
 * @author James
 */
public class ResourceException extends RuntimeException {
    public ResourceException() {
    }

    public ResourceException(String message) {
        super(message);
    }

    public ResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
