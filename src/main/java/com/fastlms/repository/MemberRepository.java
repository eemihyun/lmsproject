package com.fastlms.repository;

import com.fastlms.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
                                                    // 테이블명, key
public interface MemberRepository extends JpaRepository<Member, String> {

}
