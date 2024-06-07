package org.home.dev.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class BadRequestException extends RuntimeException {

    private int statusCode = HttpStatus.BAD_REQUEST.value();

    public BadRequestException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return statusCode;
    }

}
