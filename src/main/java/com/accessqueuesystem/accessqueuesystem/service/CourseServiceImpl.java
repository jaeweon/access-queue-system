package com.accessqueuesystem.accessqueuesystem.service;

import com.accessqueuesystem.accessqueuesystem.entity.CourseEntity;
import com.accessqueuesystem.accessqueuesystem.entity.CourseRegistrationEntity;
import com.accessqueuesystem.accessqueuesystem.entity.UserEntity;
import com.accessqueuesystem.accessqueuesystem.repository.CourseRepository;
import com.accessqueuesystem.accessqueuesystem.repository.RegistrationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;

    @Override
    public List<CourseEntity> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<CourseEntity> findByCourseCode(String courseCode) {
        return courseRepository.findByCourseCode(courseCode);
    }

    @Override
    @Transactional
    public CourseEntity registerCourse(String courseCode, Long userId) {
        CourseEntity course = courseRepository.findByCourseCode(courseCode)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with code: " + courseCode));

        if (registrationRepository.findByUserIdAndCourseId(userId, course.getId()).isPresent()) {
            throw new IllegalStateException("User already registered for this course");
        }

        course = course.incrementEnrollment(); // 인원 증가
        courseRepository.save(course);

        CourseRegistrationEntity registration = CourseRegistrationEntity.builder()
                .user(UserEntity.builder().id(userId).build()) // User 엔티티는 ID만 설정
                .course(course)
                .build();

        registrationRepository.save(registration);

        return course;
    }
}
