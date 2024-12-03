package com.sparta.msa_exam.order.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S-001", "서버 오류입니다"),

    NOT_EXIST(HttpStatus.NOT_FOUND, "O-001", "유효하지 않은 상품입니다"),
    ORDER_NOT_EXIST(HttpStatus.NOT_FOUND, "O-002", "주문이 존재하지 않습니다"),
    ORDER_NOT_MATCH_USER(HttpStatus.FORBIDDEN, "O-003", "주문자가 일치하지 않습니다");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
