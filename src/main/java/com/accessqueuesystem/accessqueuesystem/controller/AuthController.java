package com.accessqueuesystem.accessqueuesystem.controller;

import com.accessqueuesystem.accessqueuesystem.dto.LoginRequest;
import com.accessqueuesystem.accessqueuesystem.dto.RegisterRequest;
import com.accessqueuesystem.accessqueuesystem.service.UserService;
import com.accessqueuesystem.accessqueuesystem.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

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
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // 로그인 성공 시 접속자 대기 페이지로 리다이렉트
            return "redirect:/?user_id=" + request.getUsername();

        } catch (AuthenticationException e) {
            // 로그인 실패 시 다시 로그인 페이지로 이동
            return "redirect:/login?error=true";
        }
    }
}
