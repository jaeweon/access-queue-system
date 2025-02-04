package com.accessqueuesystem.accessqueuesystem.controller;

import com.accessqueuesystem.accessqueuesystem.dto.LoginRequest;
import com.accessqueuesystem.accessqueuesystem.dto.RegisterRequest;
import com.accessqueuesystem.accessqueuesystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegisterRequest request, Model model) {
        try {
            userService.registerUser(request);
            return "redirect:/login?success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest request) {
        try {

            return "redirect:/?user_id=" + request.getUsername();
        } catch (AuthenticationException e) {

            return "redirect:/login?error=true";
        }
    }
}
