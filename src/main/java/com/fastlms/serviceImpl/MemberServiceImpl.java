package com.fastlms.serviceImpl;

import com.fastlms.components.MailComponents;
import com.fastlms.dto.MemberInput;
import com.fastlms.dto.ResetPasswordInput;
import com.fastlms.entity.Member;
import com.fastlms.exception.MemberNotEmailAuthException;
import com.fastlms.repository.MemberRepository;
import com.fastlms.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

        String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());
        String uuid = UUID.randomUUID().toString();

        Member member = Member.builder()
                .userId(parameter.getUserId())
                .userName(parameter.getUserName())
                .phone(parameter.getPhone())
                .password(encPassword)
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .build();

        /*
        Member member = new Member();
        member.setUserId(parameter.getUserId());
        member.setUserName(parameter.getUserName());
        member.setPassword(parameter.getPassword());
        member.setPhone(parameter.getPhone());
        member.setRegDt(LocalDateTime.now());
        member.setEmailAuthYn(false);
        member.setEmailAuthKey(UUID.randomUUID().toString());
        */

        memberRepository.save(member);

        String email = parameter.getUserId();
        String subject = "사이트 가입을 축하드립니다. 제목";
        String text = "<p>사이트 가입을 축하드립니다. 본문</p><p>아래 링크를 클릭하셔서 가입을 완료하세요.</p>"
                +"<div> <a target='_blank' href='http://localhost:8080/member/email-auth?id=" + uuid+"'>가입 완료 </a></div>";
        mailComponents.sendMail(email, subject, text);

        return true;
    }

    @Override
    public boolean emailAuth(String uuid) {
        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);

        if(!optionalMember.isPresent()){
            return false;
        }

        Member member = optionalMember.get();
        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());
        memberRepository.save(member);

        return true;
    }

    // 비밀번호 초기화 메서드
    @Override
    public boolean sendResetPassword(ResetPasswordInput resetPasswordInput) {
        Optional<Member> optionalMember = memberRepository.findByUserIdAndUserName(resetPasswordInput.getUserId(), resetPasswordInput.getUserName());

        if( !optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member= optionalMember.get();
        String uuid = UUID.randomUUID().toString();

        member.setResetPasswordKey(uuid);
        member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
        memberRepository.save(member);

        String email = resetPasswordInput.getUserId();
        String subject = "[mhlms] 비밀번호 초기화 메일 입니다.";
        String text = "<p>mhlms 비밀번호 초기화 메일입니다.</p><p>아래 링크를 클릭하셔서 비밀번호를 초기화 해주세요.</p>"
                +"<div> <a target='_blank' href='http://localhost:8080/member/reset/password?id=" + uuid+"'> 비밀번호 초기화 링크 </a></div>";
        mailComponents.sendMail(email, subject, text);

        return true;
    }

    @Override
    public boolean resetPassword(String uuid, String password) {
        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);

        if( !optionalMember.isPresent() ) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        // 초기화시 초기화된 날짜가 유효한지 체크
        if ( member.getResetPasswordLimitDt() == null ) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        if( member.getResetPasswordLimitDt().isBefore(LocalDateTime.now()) ){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }
        
        System.out.println("지지지지지진짜 진입");

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        member.setPassword(encPassword);
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null);
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean checkResetPassword(String uuid) {
        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);

        if( !optionalMember.isPresent()) {
            return false;
        }

        Member member = optionalMember.get();

        // 초기화시 초기화된 날짜가 유효한지 체크
        if (member.getResetPasswordLimitDt() == null) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        return true;
    }

    // username: email
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findById(username);

        if( !optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        if(!member.isEmailAuthYn()) { // true가 아니면
            throw new MemberNotEmailAuthException("이메일 활성화 이후에 로그인을 해주세요.");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
    }
}
