package com.accessqueuesystem.accessqueuesystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String courseCode; // 과목 코드

    @Column(nullable = false)
    private String courseName; // 과목명

    @Column(nullable = false)
    private int maxCapacity; // 최대 수강 가능 인원

    @Column(nullable = false)
    private int currentEnrolled; // 현재 등록된 인원

    @Column(nullable = false)
    private int credit; // 학점

    @Column(nullable = false)
    private String professorName; // 교수 이름

    @Column(nullable = false)
    private String schedule; // 시간표 정보

    @Column(nullable = false)
    private String location; // 강의실 위치

    @Column(nullable = false)
    private String department; // 학과

    @Column(nullable = false)
    private String gradeLevel; // 학년

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseRegistrationEntity> registrations;

    public CourseEntity incrementEnrollment() {
        if (this.currentEnrolled >= this.maxCapacity) {
            throw new IllegalStateException("Maximum capacity reached for course: " + this.courseName);
        }
        return CourseEntity.builder()
                .id(this.id)
                .courseCode(this.courseCode)
                .courseName(this.courseName)
                .maxCapacity(this.maxCapacity)
                .currentEnrolled(this.currentEnrolled + 1)
                .credit(this.credit)
                .professorName(this.professorName)
                .schedule(this.schedule)
                .location(this.location)
                .department(this.department)
                .gradeLevel(this.gradeLevel)
                .registrations(this.registrations)
                .build();
    }
}
