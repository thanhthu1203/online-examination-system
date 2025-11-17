package com.yourexam.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Student")
public class Student {
    @Id
    @Column(length = 11)
    private String Student_ID;

    private String SName;
    private String Phone;
    private String Password; // hashed

    @ManyToMany
    @JoinTable(name = "Student_has_Course",
        joinColumns = @JoinColumn(name = "Student_Student_ID"),
        inverseJoinColumns = @JoinColumn(name = "Course_Course_ID")
    )
    private Set<Course> courses = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "Student_has_Exam",
        joinColumns = @JoinColumn(name = "Student_Student_ID"),
        inverseJoinColumns = @JoinColumn(name = "Exam_Exam_ID")
    )
    private Set<Exam> exams = new HashSet<>();

    public String getStudent_ID(){return Student_ID;}
    public void setStudent_ID(String id){this.Student_ID = id;}
    public String getSName(){return SName;}
    public void setSName(String s){this.SName = s;}
    public String getPhone(){return Phone;}
    public void setPhone(String p){this.Phone=p;}
    public String getPassword(){return Password;}
    public void setPassword(String pw){this.Password = pw;}
    public Set<Course> getCourses(){return courses;}
    public void setCourses(Set<Course> c){this.courses=c;}
    public Set<Exam> getExams(){return exams;}
    public void setExams(Set<Exam> e){this.exams=e;}
}