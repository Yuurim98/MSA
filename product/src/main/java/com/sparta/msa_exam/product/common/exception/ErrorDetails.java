package com.sparta.msa_exam.product.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorDetails {

    private HttpStatus httpStatus;
    private String errorCode;
    private String errorMessage;

}
