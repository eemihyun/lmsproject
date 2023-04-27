package com.fastlms.service;

import com.fastlms.admin.dto.MemberDto;
import com.fastlms.admin.model.MemberParam;
import com.fastlms.dto.MemberInput;
import com.fastlms.dto.ResetPasswordInput;
import com.fastlms.entity.Member;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService extends UserDetailsService {
    boolean register(MemberInput parameter);

    // uuid에 해당하는 계정을 활성화 함
    boolean emailAuth(String uuid);
    boolean sendResetPassword(ResetPasswordInput resetPasswordInput);

    // 입력받은 uuid에 대해서 password 초기화
    boolean resetPassword(String uuid, String password);

    // 입력받은 uuid 값이 유효한지 확인
    boolean checkResetPassword(String uuid);

    // 회원 목록 (관리자 모드에서만 사용 가능)
    List<MemberDto> list(MemberParam parameter);

    // 회원 상세 정보
    MemberDto detail(String userId);

    // 회원 상태 변경
    boolean updateStatus(String userId, String userStatus);

    // 회원 비밀번호 초기화
    boolean updatePassword(String userId, String password);
}
