package com.fastlms.repository;

import com.fastlms.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 테이블명, key
public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByEmailAuthKey(String emailAuthKey);
}
