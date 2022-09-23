package com.trips.create_service.exceptions;

/**
 * Created by Hardik
 */
public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String message) {
        super(message);
    }

}
