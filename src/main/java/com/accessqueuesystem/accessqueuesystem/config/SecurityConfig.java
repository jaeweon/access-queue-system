package com.accessqueuesystem.accessqueuesystem.config;

import com.accessqueuesystem.accessqueuesystem.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity // 메소드 수준의 보안을 활성화 (예: @PreAuthorize)
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
                .authorizeHttpRequests(auth -> auth
                        // 허용할 경로들
                        .requestMatchers(
                                "/register", // 회원가입 페이지
                                "/login", // 로그인 페이지
                                "/api/v1/auth/register", // 회원가입 API
                                "/api/v1/auth/login", // 로그인 API
                                "/", // 인덱스 페이지
                                "/css/**", // CSS 파일
                                "/js/**", // JavaScript 파일
                                "/images/**", // 이미지 파일
                                "/webjars/**", // 웹 자르 파일
                                "/waiting-room", // 대기실 페이지
                                "/api/v1/queue/**" // 대기열 관련 API
                        ).permitAll()
                        .anyRequest().authenticated() // 나머지 모든 요청은 인증 필요
                )
                .formLogin(form -> form
                        .loginPage("/login") //  로그인 페이지
                        .defaultSuccessUrl("/", true) // 로그인 성공 시 이동할 기본 경로
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 경로
                        .logoutSuccessUrl("/login") // 로그아웃 성공 시 이동 경로
                        .permitAll()
                );

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}