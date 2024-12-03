package com.sparta.msa_exam.order.common.exception;

import java.util.List;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;
    private final List<Long> invalidProductIds;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.invalidProductIds = null;
    }

    public CustomException(ErrorCode errorCode, List<Long> invalidProductIds) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.invalidProductIds = invalidProductIds;
    }

}
