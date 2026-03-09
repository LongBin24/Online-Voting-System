package com.khmervote.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";    }

    @GetMapping("/ballot")
    public String ballot() {
        return "ballot";
    }
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
    @GetMapping("/verify")
    public String verifyPage() {
        return "verify";
    }
    @GetMapping("/admin/dashboard")
    public String admindashboard(){
        return "amin-candidates";
    }
}
