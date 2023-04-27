package com.fastlms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;


// 회원 상태 인터페이스
public interface MemberCode {
    String MEMBER_STATUS_REQ = "REG"; // 현재 회원가입 요청중 (이메일 인증 전)
    String MEMBER_STATUS_ING = "ING"; // 현재 이용중인 상태
    String MEMBER_STATUS_STOP ="STOP"; // 이용 정지

}
