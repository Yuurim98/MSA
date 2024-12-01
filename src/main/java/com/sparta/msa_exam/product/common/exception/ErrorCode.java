package com.sparta.msa_exam.product.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S-001", "서버 오류입니다"),

    ACCESS_DENIED(HttpStatus.FORBIDDEN, "P-001", "권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
