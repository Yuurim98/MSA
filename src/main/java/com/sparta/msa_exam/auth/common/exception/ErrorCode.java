package com.sparta.msa_exam.auth.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S-001", "서버 오류입니다"),

    DUPLICATE_USER(HttpStatus.CONFLICT, "U-001", "이미 존재하는 사용자입니다"),
    USER_NOTFOUND(HttpStatus.NOT_FOUND, "U-002", "존재하지 않는 사용자입니다");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
