package com.fastlms.service;

import com.fastlms.dto.MemberInput;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {
    boolean register(MemberInput parameter);

    // uuid에 해당하는 계정을 활성화 함
    boolean emailAuth(String uuid);
}
