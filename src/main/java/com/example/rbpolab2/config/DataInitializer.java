package com.example.rbpolab2.config;

import com.example.rbpolab2.model.User;
import com.example.rbpolab2.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!userRepository.existsByEmail("admin@system.local")) {
            User admin = new User();
            admin.setFullName("Главный Администратор");
            admin.setEmail("admin@system.local");
            admin.setPassword(passwordEncoder.encode("AdminPass123!"));
            admin.setRole("ROLE_ADMIN");
            admin.setEnabled(true);
            admin.setAccountNonLocked(true);
            admin.setCreatedAt(LocalDateTime.now());
            userRepository.save(admin);
        }

        if (!userRepository.existsByEmail("expert@system.local")) {
            User expert = new User();
            expert.setFullName("Профессор Экспертов");
            expert.setEmail("expert@system.local");
            expert.setPassword(passwordEncoder.encode("ExpertPass123!"));
            expert.setRole("ROLE_EXPERT");
            expert.setEnabled(true);
            expert.setAccountNonLocked(true);
            expert.setCreatedAt(LocalDateTime.now());
            userRepository.save(expert);
        }
    }
}