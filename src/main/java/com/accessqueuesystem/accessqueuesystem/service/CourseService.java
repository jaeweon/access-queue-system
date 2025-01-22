package com.accessqueuesystem.accessqueuesystem.service;

import com.accessqueuesystem.accessqueuesystem.entity.CourseEntity;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<CourseEntity> findAll();
    Optional<CourseEntity> findByCourseCode(String courseCode);
    CourseEntity registerCourse(String courseCode, Long userId);
}
