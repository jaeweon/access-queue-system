package com.accessqueuesystem.accessqueuesystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
}