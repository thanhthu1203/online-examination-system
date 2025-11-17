package com.yourexam.controller;

import com.yourexam.model.Exam;
import com.yourexam.model.Student;
import com.yourexam.repository.ExamRepository;
import com.yourexam.repository.StudentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.transaction.Transactional;

import java.util.List;

@RestController
@RequestMapping("/exams")
public class ExamController {
    private final ExamRepository examRepository;
    private final StudentRepository studentRepository;

    public ExamController(ExamRepository examRepository, StudentRepository studentRepository){
        this.examRepository = examRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Exam> list() { return examRepository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> get(@PathVariable String id){ return examRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }

    @PostMapping
    public Exam create(@RequestBody Exam e){ return examRepository.save(e); }

    @PutMapping("/{id}")
    public Exam update(@PathVariable String id, @RequestBody Exam payload){
        Exam ex = examRepository.findById(id).orElseThrow();
        ex.setEType(payload.getEType());
        ex.setRoomNumber(payload.getRoomNumber());
        ex.setTestDateDATETIME(payload.getTestDateDATETIME());
        ex.setDurationMinutes(payload.getDurationMinutes());
        return examRepository.save(ex);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){ examRepository.deleteById(id); }

    @PostMapping("/{id}/register")
    @Transactional
    public ResponseEntity<?> register(@PathVariable String id, Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).build();
        String current = authentication.getName();
        Student s = studentRepository.findById(current).orElse(null);
        if (s == null) return ResponseEntity.status(403).body("Only students may register");
        Exam e = examRepository.findById(id).orElse(null);
        if (e == null) return ResponseEntity.notFound().build();
        s.getExams().add(e);
        studentRepository.save(s);
        return ResponseEntity.ok().build();
    }
}