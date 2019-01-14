package org.example.server;

public enum HttpResponse {
    OK("Ok", 200),
    BAD_REQUEST("Bad request", 400),
    NOT_FOUND("Not found", 404),
    METHOD_NOT_ALLOWED("Method not allowed", 405),
    INTERNAL_SERVER_ERROR("Internal server error", 500);

    private String message;
    private int httpStatus;

    HttpResponse(String message, int httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return httpStatus;
    }
}
