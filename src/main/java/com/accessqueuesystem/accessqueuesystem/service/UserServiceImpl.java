package com.accessqueuesystem.accessqueuesystem.service;

import com.accessqueuesystem.accessqueuesystem.dto.RegisterRequest;
import com.accessqueuesystem.accessqueuesystem.entity.UserEntity;
import com.accessqueuesystem.accessqueuesystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalStateException("Username already exists.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("Email already exists.");
        }

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(UserEntity.Role.STUDENT)
                .build();

        userRepository.save(user);
//        generateDummyUsers(request);
    }

//    private void generateDummyUsers(RegisterRequest request) {
//        // 사용자 정의 더미 데이터 생성 로직
//        for (int i = 1; i <= 1000; i++) {
//            String dummyUsername = String.format("dummy_user_%d", i);
//            String dummyEmail = String.format("dummy_user_%d@example.com", i);
//
//            // 중복 검사 후 삽입
//            if (!userRepository.existsByUsername(dummyUsername) && !userRepository.existsByEmail(dummyEmail)) {
//                UserEntity dummyUser = UserEntity.builder()
//                        .username(dummyUsername)
//                        .password(passwordEncoder.encode("defaultPassword123")) // 기본 비밀번호 설정
//                        .email(dummyEmail)
//                        .role(UserEntity.Role.STUDENT)
//                        .build();
//
//                userRepository.save(dummyUser);
//            }
//        }
//    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found."));
    }
}
