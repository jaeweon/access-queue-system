package com.accessqueuesystem.accessqueuesystem.controller;

import com.accessqueuesystem.accessqueuesystem.entity.CourseEntity;
import com.accessqueuesystem.accessqueuesystem.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<CourseEntity> getAllCourses() {
        return courseService.findAll();
    }

    @GetMapping("/{courseCode}")
    public CourseEntity getCourse(@PathVariable String courseCode) {
        return courseService.findByCourseCode(courseCode)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with code: " + courseCode));
    }

    @PostMapping("/register")
    public CourseEntity registerForCourse(@RequestParam String courseCode, @RequestParam Long userId) {
        return courseService.registerCourse(courseCode, userId);
    }
}