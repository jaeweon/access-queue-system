package com.accessqueuesystem.accessqueuesystem.repository;

import com.accessqueuesystem.accessqueuesystem.entity.CourseRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<CourseRegistrationEntity, Long> {
    Optional<CourseRegistrationEntity> findByUserIdAndCourseId(Long userId, Long courseId);
}
