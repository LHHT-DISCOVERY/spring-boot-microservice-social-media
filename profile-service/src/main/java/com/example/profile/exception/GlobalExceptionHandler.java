package com.example.profile.exception;

import com.example.profile.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingException(RuntimeException exception){
        log.info(exception.getMessage());
        log.info(exception.toString());
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

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.PROFILE_ERROR_ACCESS_DENIED.getCode());
        apiResponse.setMessage(ErrorCode.PROFILE_ERROR_ACCESS_DENIED.getMessage());
        return ResponseEntity.status(ErrorCode.PROFILE_ERROR_ACCESS_DENIED.getHttpStatus()).body(apiResponse);
    }
}
