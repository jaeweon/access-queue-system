package com.accessqueuesystem.accessqueuesystem.controller;

import com.accessqueuesystem.accessqueuesystem.entity.CourseEntity;
import com.accessqueuesystem.accessqueuesystem.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthPageController {

    private final CourseService courseService;

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/course")
    public String coursePage(Model model) {
//        List<CourseEntity> courses = courseService.findAll();
//        model.addAttribute("courses", courses);
        return "course";
    }
}