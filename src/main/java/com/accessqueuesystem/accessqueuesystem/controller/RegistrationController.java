package com.accessqueuesystem.accessqueuesystem.controller;

import com.accessqueuesystem.accessqueuesystem.dto.RegistrationRequest;
import com.accessqueuesystem.accessqueuesystem.entity.CourseRegistrationEntity;
import com.accessqueuesystem.accessqueuesystem.service.RegistrationService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping("/{userId}/{courseId}")
    public CourseRegistrationEntity getRegistration(@PathVariable Long userId, @PathVariable Long courseId) {
        return registrationService.findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() -> new IllegalArgumentException("Registration not found for userId: " + userId + " and courseId: " + courseId));
    }

    @PostMapping
    public CourseRegistrationEntity registerCourse(@RequestBody RegistrationRequest request) {
        return registrationService.register(request.getUserId(), request.getCourseId());
    }
}

