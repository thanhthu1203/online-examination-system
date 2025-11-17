package com.yourexam.service;

import com.yourexam.dto.AuthResponse;
import com.yourexam.model.Admin;
import com.yourexam.model.Student;
import com.yourexam.repository.AdminRepository;
import com.yourexam.repository.StudentRepository;
import com.yourexam.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(StudentRepository studentRepository, AdminRepository adminRepository,
                       BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.studentRepository = studentRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse login(String username, String password) {
        Admin admin = adminRepository.findById(username).orElse(null);
        if (admin != null && passwordEncoder.matches(password, admin.getPassword())) {
            String token = jwtUtil.generateToken(admin.getAdmin_ID(), "ADMIN");
            return new AuthResponse(token, "ADMIN");
        }
        Student student = studentRepository.findById(username).orElse(null);
        if (student != null && passwordEncoder.matches(password, student.getPassword())) {
            String token = jwtUtil.generateToken(student.getStudent_ID(), "STUDENT");
            return new AuthResponse(token, "STUDENT");
        }
        throw new RuntimeException("Invalid credentials");
    }

    public Student registerStudent(Student s) {
        s.setPassword(passwordEncoder.encode(s.getPassword()));
        return studentRepository.save(s);
    }
}