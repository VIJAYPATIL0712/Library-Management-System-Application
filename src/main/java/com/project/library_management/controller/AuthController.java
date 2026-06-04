package com.project.library_management.controller;

import com.project.library_management.dto.auth.LoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("login" , new LoginDto());
        return "auth/loginForm";
    }

}
