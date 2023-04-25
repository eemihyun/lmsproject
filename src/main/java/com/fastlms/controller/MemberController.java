package com.fastlms.controller;

import com.fastlms.dto.MemberInput;
import com.fastlms.dto.ResetPasswordInput;
import com.fastlms.entity.Member;
import com.fastlms.repository.MemberRepository;
import com.fastlms.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

// @RequiredArgsConstructor: 생성자를 자동으로 생성해서 주입하는 어노테이션
@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;

    // 로그인 페이지
    @RequestMapping("/member/login")
    public String login() {
        return "member/login";
    }

    //비밀번호 찾기
    @GetMapping("/member/find-password")
    public String findPassword() {
        return "member/find_password";
    }

    @PostMapping("/member/find-password")
    public String findPasswordSubmit(ResetPasswordInput resetPasswordInput, Model model) {
        boolean result = false;
        try {
            memberService.sendResetPassword(resetPasswordInput);
            result=true;
        } catch (Exception e){

        }
        model.addAttribute("result", result);
        return "member/find_password_result";
    }

    @GetMapping("/member/info")
    public String memberInfo() {
        return "member/info";
    }


    @GetMapping("/member/register")
    public String register() {
        return "member/register";
    }

    // request WEB -> SERVER
    // response SERVER -> WEB
    @PostMapping("/member/register")
    public String registerSubmit(Model model, HttpServletRequest request, MemberInput parameter) {
        boolean result = memberService.register(parameter);

        model.addAttribute("result", result);

        return "member/register_complete";
    }

    // 기본적인 웹 주소는 예로들어 http://www.naver.com/news/list.do?id=123
    //                        프로토콜 / 대표도메인 / 전체주소      ?뒤로는 파라미터=쿼리스트링

    @GetMapping("/member/email-auth")
    public String emailAuth(HttpServletRequest request, Model model) {
        String uuid = request.getParameter("id");
        System.out.println(uuid);

        boolean result = memberService.emailAuth(uuid);

        model.addAttribute("result", result);

        return "member/email_auth";
    }

    // 비밀번호 링크 타고 진입시
    @GetMapping("/member/reset/password")
    public String resetPassword(Model model,HttpServletRequest request){
        System.out.println("비밀번호 get페이지 진입");
        String uuid = request.getParameter("id");
        model.addAttribute("uuid",uuid);

        boolean result = memberService.checkResetPassword(uuid);
        model.addAttribute("result", result);
        return "member/reset_password";
    }

    @PostMapping("/member/reset/password")
    public String resetPasswordSubmit(Model model, ResetPasswordInput parameter) {
        boolean result = true;
        try {
            System.out.println("비밀번호 초기화 진입");
             memberService.resetPassword(parameter.getId(), parameter.getPassword());
             result = true;
        } catch (Exception e) {

        }

        model.addAttribute("result", result);
        return "member/reset_password_result";
    }
}
