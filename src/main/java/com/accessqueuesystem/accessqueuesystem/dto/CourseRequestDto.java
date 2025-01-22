package com.accessqueuesystem.accessqueuesystem.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CourseRequestDto {
    private Long userId;
    private Long courseId;
}
