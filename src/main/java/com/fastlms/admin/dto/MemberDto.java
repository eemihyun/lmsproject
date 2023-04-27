package com.fastlms.admin.dto;

import com.fastlms.entity.Member;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor // 전체 속성에 대한 값을 생성해주는 어노테이션
@NoArgsConstructor  // 생성자를 자동으로 생성해주는 어노테이션
public class MemberDto {
    public String userId;
    public String userName;
    public String phone;
    public String password;
    public LocalDateTime regDt;

    public LocalDateTime emailAuthDt;
    public String emailAuthKey;
    public boolean emailAuthYn;

    public String resetPasswordKey;
    public LocalDateTime resetPasswordLimitDt;

    public boolean adminYn;
    String userStatus;

    long totalCount;
    long seq;

    public static MemberDto of(Member member) {

        return MemberDto.builder()
                .userId(member.getUserId())
                .userName(member.getUserName())
                //.password(member.getPassword())
                .phone(member.getPhone())
                .regDt(member.getRegDt())
                .emailAuthYn(member.isEmailAuthYn())
                .emailAuthDt(member.getEmailAuthDt())
                .emailAuthKey(member.getEmailAuthKey())
                .resetPasswordKey(member.getResetPasswordKey())
                .resetPasswordLimitDt(member.getResetPasswordLimitDt())
                .adminYn(member.isAdminYn())
                .userStatus(member.getUserStatus())
                .build();
    }
}
