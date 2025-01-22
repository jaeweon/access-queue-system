package com.accessqueuesystem.accessqueuesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CourseResponseDto {
    private Long id;
    private String name;
    private String code;
    private int limit;
    private int registeredUsers;
}
