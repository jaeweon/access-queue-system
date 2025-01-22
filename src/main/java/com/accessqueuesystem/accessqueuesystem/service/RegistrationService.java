package com.accessqueuesystem.accessqueuesystem.service;

import com.accessqueuesystem.accessqueuesystem.entity.CourseRegistrationEntity;

import java.util.Optional;

public interface RegistrationService {
    Optional<CourseRegistrationEntity> findByUserIdAndCourseId(Long userId, Long courseId);
    CourseRegistrationEntity register(Long userId, Long courseId);
}
