package com.yourexam.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Exam")
public class Exam {
    @Id
    @Column(length=7)
    private String Exam_ID;

    private String EType;
    private String RoomNumber;

    @Column(name = "TestDateDATETIME")
    private LocalDateTime TestDateDATETIME;

    private Integer DurationMinutes;

    @ManyToOne
    @JoinColumn(name = "Course_Course_ID")
    private Course course;

    @ManyToMany(mappedBy = "exams")
    private Set<Student> students = new HashSet<>();

    public String getExam_ID(){return Exam_ID;}
    public void setExam_ID(String id){this.Exam_ID=id;}
    public String getEType(){return EType;}
    public void setEType(String t){this.EType=t;}
    public String getRoomNumber(){return RoomNumber;}
    public void setRoomNumber(String r){this.RoomNumber=r;}
    public LocalDateTime getTestDateDATETIME(){return TestDateDATETIME;}
    public void setTestDateDATETIME(LocalDateTime d){this.TestDateDATETIME=d;}
    public Integer getDurationMinutes(){return DurationMinutes;}
    public void setDurationMinutes(Integer m){this.DurationMinutes=m;}
    public Course getCourse(){return course;}
    public void setCourse(Course c){this.course=c;}
}