package com.fastlms;

// MainPage 클래스를 만든 목적은
// 매핑하기 위해
// 주소와(논리적인 인터넷 주소) 물리적인파일(프로그래밍을 해야하니까) 매핑

import com.fastlms.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//
// 하나의 주소에 대해서
// 어디서 매핑? 누가 매핑?
// @RequestMapping: 요청에 대한 매핑을 해주겠다는 어노테이션

@RequiredArgsConstructor
@Controller
public class MainController {
    private final MailComponents mailComponents;
    @RequestMapping("/")
    public String index() {
        // mailComponents.sendMailTest();
        String email="dlalgus95@naver.com";
        String subject="안녕하세요 이미현입니다. ";
        String text = "<p>ㅎㅇㅎㅇ</p>";
        mailComponents.sendMail(email, subject, text);
        return "index";
    }


}
