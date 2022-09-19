package com.othkkartho.vtuber_site_v2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectController {
    @RequestMapping("/")
    public String index() {
        return "/index";
    }

    @RequestMapping("/user/sign-in")
    public String SignIn() {
        return "/member/memberLoginForm";
    }

    @RequestMapping("/user/sign-up")
    public String SignUp() {
        return "/member/memberJoinForm";
    }
}
