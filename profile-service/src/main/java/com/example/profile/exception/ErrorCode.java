package com.example.profile.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public enum ErrorCode {
    PROFILE_DOES_NOT_EXIST(1001, "PROFILE_DOES_NOT_EXIST", HttpStatus.NOT_FOUND),
    PROFILE_ALREADY_EXISTS(1002, "PROFILE_ALREADY_EXISTS", HttpStatus.CONFLICT),
    PROFILE_DOES_NOT_MATCH(1004, "PROFILE_DOES_NOT_MATCH", HttpStatus.BAD_REQUEST),
    PROFILE_ERROR_SERVER(9999, "Exception cause by System" , HttpStatus.INTERNAL_SERVER_ERROR)
    ;
    int code;
    String message;
    HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
