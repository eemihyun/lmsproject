package com.fastlms.service;

import com.fastlms.dto.MemberInput;
import com.fastlms.dto.ResetPasswordInput;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {
    boolean register(MemberInput parameter);

    // uuid에 해당하는 계정을 활성화 함
    boolean emailAuth(String uuid);
    boolean sendResetPassword(ResetPasswordInput resetPasswordInput);

    // 입력받은 uuid에 대해서 password 초기화
    boolean resetPassword(String uuid, String password);

    // 입력받은 uuid 값이 유효한지 확인
    boolean checkResetPassword(String uuid);
}
