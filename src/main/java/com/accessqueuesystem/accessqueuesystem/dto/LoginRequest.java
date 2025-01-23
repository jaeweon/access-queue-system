package com.accessqueuesystem.accessqueuesystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class LoginRequest {
    private String username;
    private String password;
}
