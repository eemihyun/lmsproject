package com.fastlms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member implements MemberCode {
    @Id
    private String userId;
    private String userName;
    private String phone;
    private String password;
    private LocalDateTime regDt;

    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt; //이메일 인증한 날짜
    private String emailAuthKey;

    private String resetPasswordKey;
    private LocalDateTime resetPasswordLimitDt; // 비번유효날짜
    
    // 관리자 여부
    private boolean adminYn;

    // 회원 상태 (가능, 차단)
    private String userStatus;


}
