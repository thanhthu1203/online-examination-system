package com.yourexam.controller;

import com.yourexam.model.Course;
import com.yourexam.model.Student;
import com.yourexam.repository.CourseRepository;
import com.yourexam.repository.StudentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.transaction.Transactional;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseController(CourseRepository courseRepository, StudentRepository studentRepository){
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Course> list() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> get(@PathVariable String id) {
        return courseRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Course create(@RequestBody Course c) {
        return courseRepository.save(c);
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable String id, @RequestBody Course payload) {
        Course existing = courseRepository.findById(id).orElseThrow();
        existing.setCName(payload.getCName());
        return courseRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        courseRepository.deleteById(id);
    }

    @PostMapping("/{id}/enroll")
    @Transactional
    public ResponseEntity<?> enroll(@PathVariable String id, Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).build();
        String current = authentication.getName();
        Student s = studentRepository.findById(current).orElse(null);
        if (s == null) return ResponseEntity.status(403).body("Only students may enroll");
        Course c = courseRepository.findById(id).orElse(null);
        if (c == null) return ResponseEntity.notFound().build();
        s.getCourses().add(c);
        studentRepository.save(s);
        return ResponseEntity.ok().build();
    }
}