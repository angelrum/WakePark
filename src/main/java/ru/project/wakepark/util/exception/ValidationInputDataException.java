package ru.project.wakepark.util.exception;

public class ValidationInputDataException extends ApplicationException {

    public static final String EXCEPTION_VALID_TICKET_DURATION = "exception.ticket.valid.duration";

    public static final String EXCEPTION_VALID_TICKET_DURATION_FIX = "exception.ticket.valid.dur_fixed";

    public static final String EXCEPTION_VALID_CLIENT_TICKET_COUNT = "exception.client.ticket.count";

    public ValidationInputDataException(String msgCode) {
        super(ErrorType.VALIDATION_ERROR, msgCode);
    }
}
