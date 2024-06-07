package org.home.dev.dto.exception;

import org.springframework.http.HttpStatusCode;

public class ErrorResponseDto {

    private int status;
    private String message;

    public ErrorResponseDto() {
    }

    public ErrorResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
