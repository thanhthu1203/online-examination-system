package com.yourexam.repository;

import com.yourexam.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByStudent_Student_ID(String studentId);
    List<Result> findByExam_Exam_ID(String examId);
}