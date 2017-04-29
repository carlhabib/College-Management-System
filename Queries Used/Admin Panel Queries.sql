SELECT * FROM STUDENT;
SELECT * FROM STUDENT_COURSE WHERE S.studentID = selectedstudent.studentID;
INSERT INTO STUDENT VALUES(studentID, firstname, lastname, sex, dob, cGPA, majorGPA, minor, email, passwd, enrolledin, advisor);
DELETE FROM STUDENT WHERE studentID = selectedstudentID CASCADE;

SELECT * FROM FACULTY;
SELECT * FROM FACULTY_COURSE WHERE facultyID = selectedfaculty.facultyID;
INSERT INTO FACULTY VALUES (facultyID, firstname, lastname, sex, dob, salary, employement, email, passwd, office, teachesin);
DELETE FROM FACULTY WHERE facultyID = selectedfacultyID CASCADE;

SELECT * FROM COURSE;
SELECT * FROM SECTION WHERE isinstanceof = selectedcourseID.courseID;
INSERT INTO COURSE VALUES (courseID, credits, coursetitle, courselevel);
DELETE FROM COURSE WHERE courseID = selectedcourseID CASCADE;

SELECT * FROM SECTION;
SELECT * FROM SECTION WHERE isinstanceof = selectedcourseID.courseID;
INSERT INTO SECTION VALUES (crn, isinstanceof, term, sectionyear, sched, starttime, endtime, capacity, taughtby, inroom);
DELETE FROM SECTION WHERE crn = selectedsectioncrn CASCADE;

SELECT * FROM STAFF;
SELECT * FROM student_staff WHERE manager = selectedstaffid.staffid;
INSERT INTO STAFF VALUES (staffID, firstname, lastname, sex, dob, salary, employement, email, passwd, admin, worksin);
DELETE FROM STAFF WHERE staffID = selectedstaffID CASCADE;

UPDATE (STUDENT_STAFF) SET percentage = newvalue, salary = newvalue, location = newvalue, manager = newvalue WHERE (studentID = selectedstudentID);
UPDATE (FACULTY) SET salary = newvalue WHERE (facultyID = selectedfacultyID);
UPDATE (STAFF) SET passwd = newvalue WHERE (staffID = currentstaffID);

