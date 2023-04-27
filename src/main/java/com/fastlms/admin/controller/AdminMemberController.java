package com.fastlms.admin.controller;

import com.fastlms.admin.dto.MemberDto;
import com.fastlms.admin.model.MemberParam;
import com.fastlms.admin.util.PageUtil;
import com.fastlms.entity.Member;
import com.fastlms.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminMemberController {
    private final MemberService memberService;
    @GetMapping("/admin/member/list.do")
    public String list(Model model, MemberParam parameter) {

        parameter.init();

        List<MemberDto> members = memberService.list(parameter);

        long totalCount = 0;
        if(members != null && members.size() > 0 ){
            totalCount = members.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();

        PageUtil pageUtil = new PageUtil(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);
        model.addAttribute("members", members);
        model.addAttribute("pager", pageUtil.pager());
        model.addAttribute("totalCount",totalCount);


        return "admin/member/list";
    }

    @GetMapping("/admin/member/detail.do")
    public String detail(Model model, MemberParam parameter) {
        parameter.init();

        MemberDto member = memberService.detail(parameter.getUserId());
        model.addAttribute("member", member);


        return "admin/member/detail";
    }


}
