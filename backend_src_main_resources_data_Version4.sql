USE online_exam_db;

INSERT INTO OnlineWeb (W_ID, WName) VALUES ('web_main', 'University Main Website');

INSERT INTO Student (Student_ID, SName, Phone, Password) VALUES 
('s1001', 'Alice Smith', '090123456', '$2a$10$examplehash1'),
('s1002', 'Bob Johnson', '090789123', '$2a$10$examplehash2');

INSERT INTO Admin (Admin_ID, Password, OnlineWeb_W_ID) VALUES
('a001', '$2a$10$adminhash', 'web_main');

INSERT INTO Course (Course_ID, CName, OnlineWeb_W_ID) VALUES
('CS101', 'Intro to Computing', 'web_main'),
('MATH105', 'Calculus I', 'web_main');

INSERT INTO Exam (Exam_ID, EType, RoomNumber, TestDateDATETIME, DurationMinutes, Course_Course_ID) VALUES
('e1001', 'M', 'C101', '2025-12-15 09:00:00', 90, 'CS101'),
('e1002', 'F', 'A205', '2026-01-20 14:00:00', 120, 'CS101'),
('e2001', 'F', 'B100', '2026-01-22 10:00:00', 120, 'MATH105');

INSERT INTO Student_has_Course (Student_Student_ID, Course_Course_ID) VALUES
('s1001', 'CS101'),
('s1001', 'MATH105'),
('s1002', 'CS101');

INSERT INTO Student_has_Exam (Student_Student_ID, Exam_Exam_ID) VALUES
('s1001', 'e1002'),
('s1001', 'e2001'),
('s1002', 'e1002');

INSERT INTO Result (Student_ID, Exam_ID, Score) VALUES
('s1001', 'e1001', 92),
('s1002', 'e1001', 85);