package com.sparta.msa_exam.auth.entity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignInReqDto {

    @NotBlank(message = "이름을 입력하세요")
    private String name;

    @NotBlank(message = "비밀번호를 입력하세요")
    private String password;

}
