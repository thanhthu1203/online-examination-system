-- We are still using 'online_exam_db'
USE online_exam_db;

-- ---------------------------------
-- Insert Sample Data
-- ---------------------------------

-- 1. OnlineWeb
INSERT INTO OnlineWeb (W_ID, WName) VALUES 
('web_main', 'University Main Website');

-- 2. Student
INSERT INTO Student (Student_ID, SName, Phone, Password) VALUES 
('s1001', 'Alice Smith', '090123456', 'hash_pass_123'),
('s1002', 'Bob Johnson', '090789123', 'hash_pass_456');

-- 3. Admin
INSERT INTO Admin (Admin_ID, Password, OnlineWeb_W_ID) VALUES
('a001', 'admin_hash_789', 'web_main');

-- 4. Course
INSERT INTO Course (Course_ID, CName, OnlineWeb_W_ID) VALUES
('CS101', 'Intro to Computing', 'web_main'),
('MATH105', 'Calculus I', 'web_main');

-- 5. Exam
INSERT INTO Exam (Exam_ID, EType, RoomNumber, TestDateDATETIME, Duration, Course_Course_ID) VALUES
('e1001', 'M', 'C101', '2025-12-15 09:00:00', '90min', 'CS101'), -- CS101 Midterm
('e1002', 'F', 'A205', '2026-01-20 14:00:00', '120min', 'CS101'), -- CS101 Final
('e2001', 'F', 'B100', '2026-01-22 10:00:00', '120min', 'MATH105'); -- MATH105 Final

-- 6. Student_has_Course (Enrollments)
INSERT INTO Student_has_Course (Student_Student_ID, Course_Course_ID) VALUES
('s1001', 'CS101'),    -- Alice is in CS101
('s1001', 'MATH105'), -- Alice is in MATH105
('s1002', 'CS101');    -- Bob is in CS101

-- 7. Student_has_Exam (Registrations)
INSERT INTO Student_has_Exam (Student_Student_ID, Exam_Exam_ID) VALUES
('s1001', 'e1002'), -- Alice is registered for CS101 Final
('s1001', 'e2001'), -- Alice is registered for MATH105 Final
('s1002', 'e1002'); -- Bob is registered for CS101 Final

-- 8. Result (Scores for completed exams)
INSERT INTO Result (Student_ID, Exam_ID, Score) VALUES
('s1001', 'e1001', 92), -- Alice's score for the CS101 Midterm (e1001)
('s1002', 'e1001', 85); -- Bob's score for the CS101 Midterm (e1001)

-- Now you can query the data
SELECT 
    s.SName, 
    c.CName, 
    e.Exam_ID, 
    r.Score
FROM Result r
JOIN Student s ON r.Student_ID = s.Student_ID
JOIN Exam e ON r.Exam_ID = e.Exam_ID
JOIN Course c ON e.Course_Course_ID = c.Course_ID;