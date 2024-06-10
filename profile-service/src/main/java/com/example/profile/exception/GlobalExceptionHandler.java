package com.example.profile.exception;

import com.example.profile.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingException(RuntimeException exception){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.PROFILE_ERROR_SERVER.getCode());
        apiResponse.setMessage(ErrorCode.PROFILE_ERROR_SERVER.getMessage());
        return ResponseEntity.status(ErrorCode.PROFILE_ERROR_SERVER.getHttpStatus()).body(apiResponse);
    }

    @ExceptionHandler(value = ProfileException.class)
    ResponseEntity<ApiResponse> handlingProfileException(ProfileException exception){
        ApiResponse apiResponse = new ApiResponse();
        ErrorCode errorCode = exception.getErrorCode();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(apiResponse);
    }
}
