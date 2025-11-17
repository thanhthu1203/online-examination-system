package com.yourexam.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Course")
public class Course {
    @Id
    @Column(length=7)
    private String Course_ID;
    private String CName;

    @ManyToOne
    @JoinColumn(name = "OnlineWeb_W_ID")
    private OnlineWeb onlineWeb;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private Set<Exam> exams = new HashSet<>();

    public String getCourse_ID(){return Course_ID;}
    public void setCourse_ID(String id){this.Course_ID=id;}
    public String getCName(){return CName;}
    public void setCName(String n){this.CName=n;}
    public OnlineWeb getOnlineWeb(){return onlineWeb;}
    public void setOnlineWeb(OnlineWeb o){this.onlineWeb=o;}
    public Set<Exam> getExams(){return exams;}
    public void setExams(Set<Exam> e){this.exams=e;}
}