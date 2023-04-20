package com.fastlms.serviceImpl;

import com.fastlms.components.MailComponents;
import com.fastlms.dto.MemberInput;
import com.fastlms.entity.Member;
import com.fastlms.repository.MemberRepository;
import com.fastlms.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final MailComponents mailComponents;

    @Override
    public boolean register(MemberInput parameter) {

        Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
        if(optionalMember.isPresent()) {
            //현재 userId에 해당하는 데이터 존재
            return false;
        }

        String uuid = UUID.randomUUID().toString();
        Member member = new Member();
        member.setUserId(parameter.getUserId());
        member.setUserName(parameter.getUserName());
        member.setPassword(parameter.getPassword());
        member.setPhone(parameter.getPhone());
        member.setRegDt(LocalDateTime.now());
        member.setEmailAuthYn(false);
        member.setEmailAuthKey(UUID.randomUUID().toString());
        memberRepository.save(member);

        String email = parameter.getUserId();
        String subject = "사이트 가입을 축하드립니다. 제목";
        String text = "<p>사이트 가입을 축하드립니다. 본문</p><p>아래 링크를 클릭하셔서 가입을 완료하세요.</p>"
                +"<div> <a href='http://localhost:8080/member/email-auth?id=" + uuid+"'>가입 완료 </a></div>";
        mailComponents.sendMail(email, subject, text);

        return true;
    }
}
