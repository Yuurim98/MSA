package com.sparta.msa_exam.order.common.exception;

import com.sparta.msa_exam.order.common.ApiResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorDetails errorDetails = new ErrorDetails(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());

        if (e.getInvalidProductIds() != null) {
            ApiResponse<List<Long>> InvalidResponse = new ApiResponse<>("error", errorCode.getMessage(), e.getInvalidProductIds(), errorDetails);
            return ResponseEntity.status(errorCode.getHttpStatus()).body(InvalidResponse);
        }

        ApiResponse<Void> response = new ApiResponse<>("error", errorCode.getMessage(), null, errorDetails);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }



}
