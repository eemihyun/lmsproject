package com.fastlms.controller;

import com.fastlms.dto.MemberInput;
import com.fastlms.entity.Member;
import com.fastlms.repository.MemberRepository;
import com.fastlms.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

// @RequiredArgsConstructor: 생성자를 자동으로 생성해서 주입하는 어노테이션
@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;


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
}
