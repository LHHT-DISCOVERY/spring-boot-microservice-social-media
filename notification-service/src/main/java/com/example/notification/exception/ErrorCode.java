package com.example.notification.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    CAN_NOT_SENT_EMAIL(1011, "you can't send email", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1007, "unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORiZED(1008, "you do not have permition", HttpStatus.FORBIDDEN),
    UNCATEGORIZED_EXCEPTION(9999, "uncategorized exception error", HttpStatus.INTERNAL_SERVER_ERROR);
    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
