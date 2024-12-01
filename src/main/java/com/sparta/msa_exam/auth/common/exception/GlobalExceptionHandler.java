package com.sparta.msa_exam.auth.common.exception;

import com.sparta.msa_exam.auth.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();

        ErrorDetails errorDetails = new ErrorDetails(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
        ApiResponse<Void> response = new ApiResponse<>("error", errorCode.getMessage(), null, errorDetails);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }



}
