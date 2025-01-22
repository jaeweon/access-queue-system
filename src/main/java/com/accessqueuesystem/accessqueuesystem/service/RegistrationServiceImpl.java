package com.accessqueuesystem.accessqueuesystem.service;

import com.accessqueuesystem.accessqueuesystem.entity.CourseEntity;
import com.accessqueuesystem.accessqueuesystem.entity.CourseRegistrationEntity;
import com.accessqueuesystem.accessqueuesystem.entity.UserEntity;
import com.accessqueuesystem.accessqueuesystem.repository.CourseRepository;
import com.accessqueuesystem.accessqueuesystem.repository.RegistrationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final CourseRepository courseRepository;

    @Override
    public Optional<CourseRegistrationEntity> findByUserIdAndCourseId(Long userId, Long courseId) {
        return registrationRepository.findByUserIdAndCourseId(userId, courseId);
    }

    @Override
    @Transactional
    public CourseRegistrationEntity register(Long userId, Long courseId) {
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));

        if (course.getCurrentEnrolled() >= course.getMaxCapacity()) {
            throw new IllegalStateException("Course is already full: " + course.getCourseName());
        }

        if (registrationRepository.findByUserIdAndCourseId(userId, courseId).isPresent()) {
            throw new IllegalStateException("User is already registered for this course");
        }

        // 수강 인원 증가
        course = CourseEntity.builder()
                .id(course.getId())
                .courseCode(course.getCourseCode())
                .courseName(course.getCourseName())
                .maxCapacity(course.getMaxCapacity())
                .currentEnrolled(course.getCurrentEnrolled() + 1)
                .credit(course.getCredit())
                .professorName(course.getProfessorName())
                .schedule(course.getSchedule())
                .location(course.getLocation())
                .department(course.getDepartment())
                .gradeLevel(course.getGradeLevel())
                .registrations(course.getRegistrations())
                .build();

        CourseRegistrationEntity registration = CourseRegistrationEntity.builder()
                .user(UserEntity.builder().id(userId).build())
                .course(course)
                .build();

        registrationRepository.save(registration);
        courseRepository.save(course);

        return registration;
    }
}
