package ru.project.wakepark.util.exception;

public class NotFoundException extends RuntimeException {
    public static final String NOT_FOUND_EXCEPTION = "exception.common.notFound";

    public NotFoundException(String message) {
        super(message);
    }
}