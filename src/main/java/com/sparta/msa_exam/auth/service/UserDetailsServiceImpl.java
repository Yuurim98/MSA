package com.sparta.msa_exam.auth.service;

import com.sparta.msa_exam.auth.common.exception.CustomException;
import com.sparta.msa_exam.auth.common.exception.ErrorCode;
import com.sparta.msa_exam.auth.entity.User;
import com.sparta.msa_exam.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOTFOUND));

        return new org.springframework.security.core.userdetails.User(
            user.getName(),
            user.getPassword(),
            AuthorityUtils.createAuthorityList(user.getRole().getAuthority())
        );
    }
}
