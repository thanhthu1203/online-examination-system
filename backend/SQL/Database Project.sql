-- Drop the database if it already exists (for a clean start)
DROP DATABASE IF EXISTS online_exam_db;

-- Create the new database
CREATE DATABASE online_exam_db;

-- Select the database to use
USE online_exam_db;

-- ---------------------------------
-- Create Tables (in dependency order)
-- ---------------------------------

-- 1. OnlineWeb (Parent table)
CREATE TABLE OnlineWeb (
    W_ID VARCHAR(255) PRIMARY KEY,
    WName VARCHAR(255)
) ENGINE=InnoDB;

-- 2. Student (No dependencies)
CREATE TABLE Student (
    Student_ID CHAR(11) PRIMARY KEY,
    SName VARCHAR(255) NOT NULL,
    Phone VARCHAR(15),
    Password VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

-- 3. Admin (Depends on OnlineWeb)
CREATE TABLE Admin (
    Admin_ID CHAR(11) PRIMARY KEY,
    Password VARCHAR(255) NOT NULL,
    OnlineWeb_W_ID VARCHAR(255),
    FOREIGN KEY (OnlineWeb_W_ID) REFERENCES OnlineWeb(W_ID)
) ENGINE=InnoDB;

-- 4. Course (Depends on OnlineWeb)
CREATE TABLE Course (
    Course_ID CHAR(7) PRIMARY KEY,
    CName VARCHAR(255),
    OnlineWeb_W_ID VARCHAR(255),
    FOREIGN KEY (OnlineWeb_W_ID) REFERENCES OnlineWeb(W_ID)
) ENGINE=InnoDB;

-- 5. Exam (Depends on Course)
CREATE TABLE Exam (
    Exam_ID CHAR(7) PRIMARY KEY,
    EType CHAR(1),
    RoomNumber VARCHAR(10),
    TestDateDATETIME DATETIME,
    Duration VARCHAR(10),
    Course_Course_ID CHAR(7),
    FOREIGN KEY (Course_Course_ID) REFERENCES Course(Course_ID)
) ENGINE=InnoDB;

-- 6. Result (Depends on Student and Exam)
CREATE TABLE Result (
    Result_ID INT PRIMARY KEY AUTO_INCREMENT,
    Student_ID CHAR(11),
    Exam_ID CHAR(7),
    Score INT,
    FOREIGN KEY (Student_ID) REFERENCES Student(Student_ID),
    FOREIGN KEY (Exam_ID) REFERENCES Exam(Exam_ID)
) ENGINE=InnoDB;

-- 7. Student_has_Course (Junction table)
-- Connects Student and Course (Many-to-Many)
CREATE TABLE Student_has_Course (
    Student_Student_ID CHAR(11),
    Course_Course_ID CHAR(7),
    PRIMARY KEY (Student_Student_ID, Course_Course_ID),
    FOREIGN KEY (Student_Student_ID) REFERENCES Student(Student_ID),
    FOREIGN KEY (Course_Course_ID) REFERENCES Course(Course_ID)
) ENGINE=InnoDB;

-- 8. Student_has_Exam (Junction table)
-- Connects Student and Exam (e.g., to show "registered" students)
CREATE TABLE Student_has_Exam (
    Student_Student_ID CHAR(11),
    Exam_Exam_ID CHAR(7),
    PRIMARY KEY (Student_Student_ID, Exam_Exam_ID),
    FOREIGN KEY (Student_Student_ID) REFERENCES Student(Student_ID),
    FOREIGN KEY (Exam_Exam_ID) REFERENCES Exam(Exam_ID)
) ENGINE=InnoDB;