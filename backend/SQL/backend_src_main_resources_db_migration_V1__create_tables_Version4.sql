-- Flyway migration: create schema for online_exam_db
CREATE DATABASE IF NOT EXISTS online_exam_db;
USE online_exam_db;

CREATE TABLE IF NOT EXISTS OnlineWeb (
    W_ID VARCHAR(255) PRIMARY KEY,
    WName VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Student (
    Student_ID CHAR(11) PRIMARY KEY,
    SName VARCHAR(255) NOT NULL,
    Phone VARCHAR(15),
    Password VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Admin (
    Admin_ID CHAR(11) PRIMARY KEY,
    Password VARCHAR(255) NOT NULL,
    OnlineWeb_W_ID VARCHAR(255),
    FOREIGN KEY (OnlineWeb_W_ID) REFERENCES OnlineWeb(W_ID)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Course (
    Course_ID CHAR(7) PRIMARY KEY,
    CName VARCHAR(255),
    OnlineWeb_W_ID VARCHAR(255),
    FOREIGN KEY (OnlineWeb_W_ID) REFERENCES OnlineWeb(W_ID)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Exam (
    Exam_ID CHAR(7) PRIMARY KEY,
    EType CHAR(1),
    RoomNumber VARCHAR(10),
    TestDateDATETIME DATETIME,
    DurationMinutes INT,
    Course_Course_ID CHAR(7),
    FOREIGN KEY (Course_Course_ID) REFERENCES Course(Course_ID)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Result (
    Result_ID INT PRIMARY KEY AUTO_INCREMENT,
    Student_ID CHAR(11),
    Exam_ID CHAR(7),
    Score INT,
    FOREIGN KEY (Student_ID) REFERENCES Student(Student_ID),
    FOREIGN KEY (Exam_ID) REFERENCES Exam(Exam_ID)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Student_has_Course (
    Student_Student_ID CHAR(11),
    Course_Course_ID CHAR(7),
    PRIMARY KEY (Student_Student_ID, Course_Course_ID),
    FOREIGN KEY (Student_Student_ID) REFERENCES Student(Student_ID),
    FOREIGN KEY (Course_Course_ID) REFERENCES Course(Course_ID)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Student_has_Exam (
    Student_Student_ID CHAR(11),
    Exam_Exam_ID CHAR(7),
    PRIMARY KEY (Student_Student_ID, Exam_Exam_ID),
    FOREIGN KEY (Student_Student_ID) REFERENCES Student(Student_ID),
    FOREIGN KEY (Exam_Exam_ID) REFERENCES Exam(Exam_ID)
) ENGINE=InnoDB;