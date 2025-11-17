package com.yourexam.controller;

import com.yourexam.model.Exam;
import com.yourexam.model.Result;
import com.yourexam.model.Student;
import com.yourexam.repository.ExamRepository;
import com.yourexam.repository.ResultRepository;
import com.yourexam.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/results")
public class ResultController {
    private final ResultRepository resultRepository;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;

    public ResultController(ResultRepository resultRepository, StudentRepository studentRepository, ExamRepository examRepository){
        this.resultRepository = resultRepository;
        this.studentRepository = studentRepository;
        this.examRepository = examRepository;
    }

    @PostMapping
    public Result addResult(@RequestBody Result payload) {
        Student s = studentRepository.findById(payload.getStudent().getStudent_ID()).orElseThrow();
        Exam e = examRepository.findById(payload.getExam().getExam_ID()).orElseThrow();
        payload.setStudent(s);
        payload.setExam(e);
        return resultRepository.save(payload);
    }

    @GetMapping("/student/{studentId}")
    public List<Result> getByStudent(@PathVariable String studentId){
        return resultRepository.findByStudent_Student_ID(studentId);
    }

    @GetMapping("/exam/{examId}")
    public List<Result> getByExam(@PathVariable String examId){
        return resultRepository.findByExam_Exam_ID(examId);
    }

    @PostMapping(value = "/upload-csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<?> uploadCsv(@RequestPart("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Empty file");
        }
        List<String> errors = new ArrayList<>();
        int lineNo = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineNo++;
                String[] parts = line.split(",");
                if (parts.length < 3) {
                    errors.add("Line " + lineNo + ": invalid columns");
                    continue;
                }
                String studentId = parts[0].trim();
                String examId = parts[1].trim();
                String scoreStr = parts[2].trim();
                try {
                    int score = Integer.parseInt(scoreStr);
                    Student s = studentRepository.findById(studentId).orElse(null);
                    Exam e = examRepository.findById(examId).orElse(null);
                    if (s == null) { errors.add("Line " + lineNo + ": student not found " + studentId); continue; }
                    if (e == null) { errors.add("Line " + lineNo + ": exam not found " + examId); continue; }
                    Result r = new Result();
                    r.setStudent(s);
                    r.setExam(e);
                    r.setScore(score);
                    resultRepository.save(r);
                } catch (NumberFormatException nfe) {
                    errors.add("Line " + lineNo + ": invalid score " + scoreStr);
                }
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Failed to parse file: " + ex.getMessage());
        }
        if (errors.isEmpty()) return ResponseEntity.ok("Upload complete");
        return ResponseEntity.badRequest().body(errors);
    }
}