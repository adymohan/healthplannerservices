package com.ibm.healthplanner.exception;

import java.time.LocalDateTime;

public final class ExceptionResponse {

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public ExceptionResponse(LocalDateTime localDateTime, Integer status, String error,
            String message, String path) {
        this.timestamp = localDateTime;
        this.message = message;
        this.path = path;
        this.error = error;
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}
