package com.example.identity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_EXIST(1001, "username exist", HttpStatus.NOT_FOUND),
    INVALID_DOB(1010, "your age must be at least {min}", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_FOUND(1009, "Permission not found", HttpStatus.NOT_FOUND),
    INVALID_KEY(1002, "Invalid message key", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9999, "uncategorized exception error", HttpStatus.INTERNAL_SERVER_ERROR),
    USERNAME_INVALID(1003, "Username must be at least {min} character", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least {min} character", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1005, "user not found", HttpStatus.BAD_REQUEST),
    JSON_EXCEPTION(1006, "JSONException", HttpStatus.HTTP_VERSION_NOT_SUPPORTED),
    UNAUTHENTICATED(1007, "unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORiZED(1008, "you do not have permition", HttpStatus.FORBIDDEN);
    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
