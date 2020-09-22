package ru.project.wakepark.util.exception;

public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final String info;
    private final String typeMessage;
    private final String[] details;

    public ErrorInfo(CharSequence url, ErrorType type, String typeMessage, String... details) {
        this.url = url.toString();
        this.type = type;
        this.info = type.getType();
        this.typeMessage = typeMessage;
        this.details = details;
    }
}