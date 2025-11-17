package com.yourexam.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Result_ID;

    @ManyToOne
    @JoinColumn(name = "Student_ID")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "Exam_ID")
    private Exam exam;

    private Integer Score;

    public Long getResult_ID(){return Result_ID;}
    public void setResult_ID(Long id){this.Result_ID = id;}
    public Student getStudent(){return student;}
    public void setStudent(Student s){this.student=s;}
    public Exam getExam(){return exam;}
    public void setExam(Exam e){this.exam=e;}
    public Integer getScore(){return Score;}
    public void setScore(Integer s){this.Score=s;}
}