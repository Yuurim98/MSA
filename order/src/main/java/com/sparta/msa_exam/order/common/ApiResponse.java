package com.sparta.msa_exam.order.common;

import com.sparta.msa_exam.order.common.exception.ErrorDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private String status; // success, error
    private String message;
    private T data; // 성공 시 데이터
    private ErrorDetails error; // 에러 상세 정보 (예외 시)

}
