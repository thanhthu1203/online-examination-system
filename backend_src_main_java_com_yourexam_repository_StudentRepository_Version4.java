package com.yourexam.repository;

import com.yourexam.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
    Student findByStudent_ID(String id);
}