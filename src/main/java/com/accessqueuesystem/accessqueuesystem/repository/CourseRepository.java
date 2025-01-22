package com.accessqueuesystem.accessqueuesystem.repository;

import com.accessqueuesystem.accessqueuesystem.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    Optional<CourseEntity> findByCourseCode(String courseCode);
}
