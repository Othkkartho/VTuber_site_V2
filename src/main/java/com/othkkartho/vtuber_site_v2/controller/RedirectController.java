package com.othkkartho.vtuber_site_v2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectController {
    @RequestMapping("/")
    public String index() {
        return "/index";
    }

    @RequestMapping("/user/sign-up")
    public String signUp() {
        return "/member/memberJoinForm";
    }

    @RequestMapping("/user/sign-in")
    public String signIn() {
        return "/member/memberLoginForm";
    }
}
