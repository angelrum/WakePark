package ru.project.wakepark.util.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    APP_ERROR("error.appError", HttpStatus.INTERNAL_SERVER_ERROR),
    //  http://stackoverflow.com/a/22358422/548473
    DATA_NOT_FOUND("error.dataNotFound", HttpStatus.UNPROCESSABLE_ENTITY, "warning"),
    DATA_ERROR("error.dataError", HttpStatus.CONFLICT),
    VALIDATION_ERROR("error.validationError", HttpStatus.UNPROCESSABLE_ENTITY),
    WRONG_REQUEST("error.wrongRequest", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final HttpStatus status;
    private final String type;

    ErrorType(String errorCode, HttpStatus status, String type) {
        this.errorCode = errorCode;
        this.status = status;
        this.type = type;
    }

    ErrorType(String errorCode, HttpStatus status) {
        this.errorCode = errorCode;
        this.status = status;
        this.type = "error";
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }
}
