package com.yourexam.controller;

import com.yourexam.dto.AuthRequest;
import com.yourexam.dto\AuthResponse;
import com.yourexam.model.Student;
import com.yourexam.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Student> registerStudent(@RequestBody Student payload) {
        Student saved = authService.registerStudent(payload);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest body){
        AuthResponse token = authService.login(body.getUsername(), body.getPassword());
        return ResponseEntity.ok(token);
    }
}