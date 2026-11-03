package com.example.rbpolab2.controller;

import com.example.rbpolab2.dto.RegisterRequestDto;
import com.example.rbpolab2.model.User;
import com.example.rbpolab2.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Регистрация нового соискателя с проверкой пароля
    @PostMapping("/auth/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(dto));
    }

    // Получение CSRF токена для небезопасных методов (POST, PUT, DELETE, PATCH)
    @GetMapping("/csrf")
    public CsrfToken getCsrfToken(CsrfToken token) {
        return token;
    }
}