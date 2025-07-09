package com.tuniRap.collaborationManagement.exception;

public class AlreadyExistException extends RuntimeException {

    public AlreadyExistException(String message) {

        super(message);
    }

    // Constructeur avec message et cause
    public AlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructeur avec cause
    public AlreadyExistException(Throwable cause) {
        super(cause);
    }
}
