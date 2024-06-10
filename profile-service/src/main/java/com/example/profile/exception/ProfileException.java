package com.example.profile.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfileException extends RuntimeException {
    private ErrorCode errorCode;

    public ProfileException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        //keyword supper() call constructor of RuntimeException, set again message of error code
        this.errorCode = errorCode;
    }

    public ProfileException() {}
}
