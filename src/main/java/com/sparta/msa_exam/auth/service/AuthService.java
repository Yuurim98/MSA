package com.sparta.msa_exam.auth.service;

import com.sparta.msa_exam.auth.common.exception.CustomException;
import com.sparta.msa_exam.auth.common.exception.ErrorCode;
import com.sparta.msa_exam.auth.entity.Role;
import com.sparta.msa_exam.auth.entity.User;
import com.sparta.msa_exam.auth.entity.dto.SignInReqDto;
import com.sparta.msa_exam.auth.entity.dto.SignUpReqDto;
import com.sparta.msa_exam.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUpReqDto dto) {
        userRepository.findByName(dto.getName())
            .ifPresent(user -> {
                if(passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                    throw new CustomException(ErrorCode.DUPLICATE_USER);
                }
            });

        Role role = dto.getIsManager() ? Role.MANAGER : Role.USER;

        User user = User.builder()
            .name(dto.getName())
            .password(passwordEncoder.encode(dto.getPassword()))
            .role(role)
            .build();
        userRepository.save(user);
    }

    public void signIn(SignInReqDto dto) {

    }
}
