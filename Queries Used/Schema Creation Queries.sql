CREATE SCHEMA IF NOT EXISTS UNIVERSITY;
USE UNIVERSITY;

CREATE TABLE IF NOT EXISTS SCHOOL
(
schoolID VARCHAR(10) NOT NULL,
schoolname VARCHAR(255) NOT NULL,
dean VARCHAR(100),
assdean VARCHAR(100),
assocdean VARCHAR(100),
PRIMARY KEY (schoolID)
);

CREATE TABLE IF NOT EXISTS COURSE
(
courseID VARCHAR(10) NOT NULL,
credits INT,
coursetitle VARCHAR(45),
courselevel ENUM('Graduate','Undergraduate'),
PRIMARY KEY (courseID)
);

CREATE TABLE IF NOT EXISTS CLASSROOM
(
roomID VARCHAR(10) NOT NULL,
building VARCHAR(45) NOT NULL,
roomnumber INT NOT NULL,
roomcapacity INT NOT NULL,
roomtype ENUM('Auditorium', 'Laboratory', 'Conference Room', 'Lecture Room', 'Gymnasium', 'Morgue'),
PRIMARY KEY (roomID)
);

CREATE TABLE IF NOT EXISTS DEPARTMENT
(
deptID VARCHAR (3) NOT NULL,
deptname VARCHAR(45) NOT NULL,
belongsto VARCHAR(10),
PRIMARY KEY (deptID),
FOREIGN KEY (belongsto) REFERENCES SCHOOL(schoolID)
);

CREATE TABLE IF NOT EXISTS PROGRAM
(
programID VARCHAR(15) NOT NULL,
programName VARCHAR(45) NOT NULL,
programlevel ENUM('Major', 'Minor') NOT NULL,
creditsRequired INT NOT NULL,
belongsto VARCHAR (3) NOT NULL,
PRIMARY KEY (programID),
FOREIGN KEY (belongsto) REFERENCES DEPARTMENT (deptID)
);

CREATE TABLE IF NOT EXISTS FACULTY
(
facultyID VARCHAR(6) NOT NULL,
firstname VARCHAR(45) NOT NULL,
lastname VARCHAR(45) NOT NULL,
sex ENUM('M','F'),
dob DATE,
salary DOUBLE,
employment ENUM('Full Time', 'Part Time'),
email VARCHAR(255) NOT NULL,
passwd VARCHAR(13) NOT NULL,
office VARCHAR (45),
teachesin VARCHAR (3) NOT NULL,
PRIMARY KEY (facultyID),
FOREIGN KEY (teachesin) REFERENCES DEPARTMENT (deptID)
);

CREATE TABLE IF NOT EXISTS STUDENT
(
studentID VARCHAR(6) NOT NULL,
firstname VARCHAR(45) NOT NULL,
lastname VARCHAR(45) NOT NULL,
sex ENUM('M','F'),
dob DATE,
cGPA DECIMAL(3,2),
majorGPA DECIMAL(3,2),
minor VARCHAR(45),
email VARCHAR(255) NOT NULL,
passwd VARCHAR(13) NOT NULL,
enrolledin VARCHAR(15) NOT NULL,
advisor VARCHAR(6) NOT NULL,
PRIMARY KEY (studentID),
FOREIGN KEY (enrolledin) REFERENCES PROGRAM (programID),
FOREIGN KEY (advisor) REFERENCES FACULTY (facultyID)
);

CREATE TABLE IF NOT EXISTS STAFF
(
staffID VARCHAR(6) NOT NULL,
firstname VARCHAR(45) NOT NULL,
lastname VARCHAR(45) NOT NULL,
sex ENUM('M','F'),
dob DATE,
salary DOUBLE,
employment ENUM('Full Time', 'Part Time'),
email VARCHAR(255) NOT NULL,
passwd VARCHAR(13) NOT NULL,
admin BOOL,
worksin VARCHAR (3) NOT NULL,
PRIMARY KEY (staffID),
FOREIGN KEY (worksin) REFERENCES DEPARTMENT (deptID)
);

CREATE TABLE IF NOT EXISTS SECTION
(
crn INT NOT NULL,
isinstanceof VARCHAR(10) NOT NULL,
term ENUM('SPRING', 'SUMMER', 'WINTER', 'FALL'),
sectionyear INT,
sched VARCHAR(5),
starttime VARCHAR(8),
endtime VARCHAR(8),
capacity INT,
taughtby VARCHAR(6) NOT NULL,
inroom VARCHAR(10) NOT NULL,
PRIMARY KEY (crn),
FOREIGN KEY (isinstanceof) REFERENCES COURSE (courseID),
FOREIGN KEY (taughtby) REFERENCES FACULTY (facultyID),
FOREIGN KEY (inroom) REFERENCES CLASSROOM (roomID)
);

CREATE TABLE IF NOT EXISTS STUDENT_STAFF
(
studentID VARCHAR(6) NOT NULL,
manager VARCHAR(6) NOT NULL,
term ENUM('SPRING', 'SUMMER', 'WINTER', 'FALL'),
contractyear YEAR,
percentage DOUBLE,
salary INT,
location VARCHAR(45),
PRIMARY KEY (studentID, manager),
FOREIGN KEY (studentID) REFERENCES STUDENT (studentID),
FOREIGN KEY (manager) REFERENCES STAFF (staffID)
);

CREATE TABLE IF NOT EXISTS STUDENT_SECTION
(
studentID VARCHAR(6) NOT NULL,
crn INT NOT NULL,
grade VARCHAR(15),
PRIMARY KEY (studentID, crn),
FOREIGN KEY (studentID) REFERENCES STUDENT (studentID),
FOREIGN KEY (crn) REFERENCES SECTION (crn)
);

CREATE TABLE IF NOT EXISTS FACULTY_COURSE
(
courseID VARCHAR(10) NOT NULL,
facultyID VARCHAR(6) NOT NULL,
coordinator BOOL,
PRIMARY KEY (facultyID, courseID),
FOREIGN KEY (courseID) REFERENCES COURSE (courseID),
FOREIGN KEY (facultyID) REFERENCES FACULTY (facultyID)
);

CREATE TABLE IF NOT EXISTS STUDENT_COURSE
(
courseID VARCHAR(10) NOT NULL,
studentID VARCHAR(6) NOT NULL,
PRIMARY KEY (studentID, courseID),
FOREIGN KEY (studentID) REFERENCES STUDENT (studentID),
FOREIGN KEY (courseID) REFERENCES COURSE (courseID)
);

CREATE TABLE IF NOT EXISTS PROGRAM_COURSE
(
courseID VARCHAR(10) NOT NULL,
programID VARCHAR(15) NOT NULL,
PRIMARY KEY (programID, courseID),
FOREIGN KEY (courseID) REFERENCES COURSE (courseID),
FOREIGN KEY (programID) REFERENCES PROGRAM (programID)
);
INSERT INTO CLASSROOM VALUES ('Z0001', 'Zakhem', 301, 40, 'Lecture Room');
INSERT INTO CLASSROOM VALUES ('Z0027', 'Zakhem', 603, 15, 'Laboratory');
INSERT INTO CLASSROOM VALUES ('Z0015', 'Zakhem', 406, 80, 'Auditorium');
INSERT INTO CLASSROOM VALUES ('M0010', 'Medical School', 110, 20, 'Morgue');
INSERT INTO CLASSROOM VALUES ('S0014', 'Science', 404, 40, 'Lecture Room');
INSERT INTO CLASSROOM VALUES ('S0005', 'Science', 201, 15, 'Laboratory');
INSERT INTO CLASSROOM VALUES ('T0001', 'Outside', 101, 10, 'Gymnasium');

INSERT INTO SCHOOL VALUES ('SOE', 'School of Engineering', 'Dr. Georges Nasr', 'Dr. BarBar Akle', 'Dr. Raymond Ghajar');
INSERT INTO SCHOOL VALUES ('SOM', 'School of Medicine', 'Dr. Zeinat Hijazi', null, 'Dr. Sola Aoun Bahous');
INSERT INTO SCHOOL VALUES ('SOB', 'School of Buisness', 'Dr. Said Ladki', null, null);
INSERT INTO SCHOOL VALUES ('SAS', 'School of Arts and Science', 'Dr. Nashat Manour', null,'Dr. Samer Habre');

INSERT INTO COURSE VALUES ('COE 418', 3, 'Database systems', 'Undergraduate');
INSERT INTO COURSE VALUES ('MTH 207', 2, 'Discrete Structures 1', 'Undergraduate');
INSERT INTO COURSE VALUES ('MKT 201', 1, 'Introduction to Marketing', 'Undergraduate');
INSERT INTO COURSE VALUES ('MED 666', 4, 'Human Dissection', 'Undergraduate');
INSERT INTO COURSE VALUES ('COE 756', 3, 'VLSI Design', 'Graduate');
INSERT INTO COURSE VALUES ('ELE 302', 3, 'Electric Circuits II', 'Undergraduate');
INSERT INTO COURSE VALUES ('PED 401', 1, 'Introduction to tennis', 'Undergraduate');
INSERT INTO COURSE VALUES ('COE 593', 3, 'COE Application', 'Undergraduate');
INSERT INTO COURSE VALUES ('ENG 202', 3, 'English 3', 'Undergraduate');
INSERT INTO COURSE VALUES ('ECO 201', 3, 'Micro Economics', 'Undergraduate');
INSERT INTO COURSE VALUES ('ENG 101', 3, 'English 1', 'Undergraduate');
INSERT INTO COURSE VALUES ('COE 321', 3, 'Logic Design', 'Undergraduate');
INSERT INTO COURSE VALUES ('COE 312', 3, 'Data Structures', 'Undergraduate');



INSERT INTO DEPARTMENT VALUES ('ECE', 'Electrical and Computer Engineering', 'SOE');
INSERT INTO DEPARTMENT VALUES ('BUS', 'Buisness', 'SOB');
INSERT INTO DEPARTMENT VALUES ('MED', 'Medicine', 'SOM');
INSERT INTO DEPARTMENT VALUES ('MCS', 'Math and Computer Science', 'SAS');
INSERT INTO DEPARTMENT VALUES ('PED', 'Physical Education', 'SAS');
INSERT INTO DEPARTMENT VALUES ('ITD', 'Information Technology', null);
INSERT INTO DEPARTMENT VALUES ('REG', 'Registrar', null);
INSERT INTO DEPARTMENT VALUES ('PSH', 'Psychology and Health', null);
INSERT INTO DEPARTMENT VALUES ('ENG', 'English', null);

INSERT INTO PROGRAM VALUES ('M.S.COE', 'M.S. in Computer Engineering', 'Major', 60, 'ECE');
INSERT INTO PROGRAM VALUES ('B.E.COE', 'B.E. in Computer Engineering', 'Major', 150, 'ECE');
INSERT INTO PROGRAM VALUES ('B.S.CSC', 'B.S. in Computer Science', 'Major', 90, 'MCS');
INSERT INTO PROGRAM VALUES ('Min.MTH', 'Minor in Mathematics', 'Minor', 18, 'MCS');
INSERT INTO PROGRAM VALUES ('B.S.MKT', 'B.S. in Marketing', 'Major', 90, 'BUS');
INSERT INTO PROGRAM VALUES ('M.D. PHD', 'Doctor of Medicine', 'Major', 100, 'MED'); 

INSERT INTO FACULTY VALUES ('I0001', 'Joe', 'Tekli', 'M','1982-01-14', 12000.00, 'Full Time', 'jtekli@acs.edu.int', 'pass', 'B104', 'ECE');
INSERT INTO FACULTY VALUES ('I0002', 'Stephen', 'Hawkins', 'M','1982-01-14', 16000.00, 'Full Time', 'shawkins@acs.edu.int', 'pass', 'S023', 'MCS');
INSERT INTO FACULTY VALUES ('I0013', 'Steve', 'Wozniack', 'M','1965-08-14', 14000.00, 'Full Time', 'swozniack@acs.edu.int', 'pass', 'B103', 'ECE');
INSERT INTO FACULTY VALUES ('I0045', 'Tory', 'Burch', 'F','1960-08-14', 11000.00, 'Full Time', 'tburch@acs.edu.int', 'pass', 'A140', 'BUS');
INSERT INTO FACULTY VALUES ('I0034', 'Maria', 'Dubinsky', 'F', '1940-08-14', 15000.00, 'Full Time', 'dubinsky@acs.edu.int', 'pass', 'M015', 'MED');
INSERT INTO FACULTY VALUES ('I0153', 'Maria', 'Sharapova', 'F', '1982-08-14', 5000.00, 'Part Time', 'maria.sharapova@acs.edu.int', 'pass', null, 'PED');
INSERT INTO FACULTY VALUES ('I0111', 'Raghida', 'Ibrahim', 'F', '1970-08-4', 7000.00, 'Full Time', 'raghida.ibrahim@acs.edu.int', 'pass', null, 'ENG');


INSERT INTO STAFF VALUES ('ST0001', 'Bill', 'Gates', 'M','1965-08-14', 17000.00, 'Full Time', 'b.gates@acs.edu.int', 'pass', 1, 'ITD');
INSERT INTO STAFF VALUES ('ST0002', 'Hani', 'Mawlawi', 'F','1982-01-14', 8000.00, 'Full Time', 'h.mawlawi@acs.edu.int', 'pass', 0, 'ECE');
INSERT INTO STAFF VALUES ('ST031', 'Alan', 'Watts', 'M','1982-01-14', 8000.00, 'Full Time', 'a.watts@acs.edu.int', 'pass', 0, 'PSH');
INSERT INTO STAFF VALUES ('ST133', 'Clara', 'Barton', 'F','1960-08-14', 3000.00, 'Part Time', 'clara.barton@acs.edu.int', 'pass', 0, 'BUS');

INSERT INTO STUDENT VALUES ('S0001', 'Sameeh', 'Moghrabi', 'M', '1994-08-14', 3.7, 3.8, null, 'sameeh.moghrabi@acs.edu', 'pass', 'B.E.COE','I0001');
INSERT INTO STUDENT VALUES ('S0002', 'Carl', 'Habib', 'M', '1996-08-14', 3.6, 3.9, 'Min.MTH', 'carl.habib@acs.edu', 'pass', 'B.E.COE','I0013');
INSERT INTO STUDENT VALUES ('S0015', 'Amer', 'Ajouz', 'M', '1996-08-14' , 3.5, 3.5, null, 'amer.ajouz@acs.edu', 'pass', 'M.S.COE', 'I0001');
INSERT INTO STUDENT VALUES ('S0037', 'Jane', 'Doe', 'F', '1998-08-14' , 3.9, 4.0, null, 'jane.doe@acs.edu', 'pass', 'B.S.MKT', 'I0045');
INSERT INTO STUDENT VALUES ('S0044', 'Karen', 'Majdalani', 'F', '1995-08-14' , 3.9, 4.0, null, 'karen.majdalani@acs.edu', 'pass', 'B.E.COE', 'I0001');
INSERT INTO STUDENT VALUES ('S0023', 'Mary', 'Aoun', 'F', '1997-08-14' , 3.9, 4.0, null, 'mary.aoun@acs.edu', 'pass', 'M.D. PHD', 'I0001');


INSERT INTO SECTION VALUES (201611, 'COE 593', 'FALL', 2016, 'MWF', '8:00:00', '8:50:00', 40, 'I0001', 'Z0001');
INSERT INTO SECTION VALUES (201621, 'COE 418', 'FALL', 2016, 'MWF', '9:00:00', '9:50:00', 40, 'I0001', 'Z0001');
INSERT INTO SECTION VALUES (201622, 'COE 418', 'FALL', 2016, 'TR', '8:00:00', '9:15:00', 40, 'I0001', 'Z0001');
INSERT INTO SECTION VALUES (201511, 'MTH 207', 'FALL', 2015, 'MWF', '9:00:00', '9:50:00', 40, 'I0002', 'S0014');
INSERT INTO SECTION VALUES (201411, 'MTH 207', 'FALL', 2014, 'MWF', '9:00:00', '9:50:00', 40, 'I0002', 'S0014');
INSERT INTO SECTION VALUES (201521, 'MTH 207', 'SPRING', 2015, 'MWF', '9:00:00', '9:50:00', 40, 'I0002', 'S0014');
INSERT INTO SECTION VALUES (201531, 'ENG 202', 'SPRING', 2015, 'TR', '13:00:00', '14:15:00', 40, 'I0111', 'S0014');
INSERT INTO SECTION VALUES (201631, 'MKT 201', 'FALL', 2016, 'MWF', '10:00:00', '10:50:00', 40, 'I0045', 'S0014');
INSERT INTO SECTION VALUES (201311, 'PED 401', 'SPRING', 2013, 'M', '16:00:00', '17:00:00', 10, 'I0001', 'T0001');
INSERT INTO SECTION VALUES (201641, 'PED 401', 'SPRING', 2016, 'M', '16:00:00', '17:00:00', 10, 'I0001', 'T0001');
INSERT INTO SECTION VALUES (201541, 'ELE 302', 'FALL', 2015, 'TR', '09:30:00', '10:45:00', 80, 'I0013', 'Z0015');
INSERT INTO SECTION VALUES (201551, 'ELE 302', 'SPRING', 2015, 'TR', '09:30:00', '10:45:00', 80, 'I0013', 'Z0015');
INSERT INTO SECTION VALUES (201651, 'COE 756', 'FALL', 2016, 'T', '16:00:00', '19:00:00', 40, 'I0013', 'Z0001');
INSERT INTO SECTION VALUES (201671, 'ECO 201', 'FALL', 2016, 'MWF', '10:00:00', '10:50:00', 40, 'I0045', 'S0014');
INSERT INTO SECTION VALUES (201561, 'ENG 101', 'SPRING', 2015, 'TR', '14:30:00', '15:15:00', 40, 'I0111', 'S0014');
INSERT INTO SECTION VALUES (201681, 'COE 321', 'FALL', 2016, 'MWF', '12:00:00', '12:50:00', 40, 'I0002', 'S0014');
INSERT INTO SECTION VALUES (201691, 'COE 312', 'FALL', 2016, 'TR', '9:30:00', '10:45:00', 40, 'I0001', 'Z0001');
INSERT INTO SECTION VALUES (2016101, 'MED 666', 'FALL', 2016, 'W', '16:00:00', '19:00:00', 20, 'I0034', 'M0010');



INSERT INTO FACULTY_COURSE VALUES ('COE 418', 'I0001', 1);
INSERT INTO FACULTY_COURSE VALUES ('COE 593', 'I0001', 1);
INSERT INTO FACULTY_COURSE VALUES ('COE 312', 'I0001', 1);
INSERT INTO FACULTY_COURSE VALUES ('MTH 207', 'I0002', 1);
INSERT INTO FACULTY_COURSE VALUES ('COE 321', 'I0002', 0);
INSERT INTO FACULTY_COURSE VALUES ('ENG 202', 'I0111', 0);
INSERT INTO FACULTY_COURSE VALUES ('ENG 101', 'I0111', 0);
INSERT INTO FACULTY_COURSE VALUES ('MKT 201', 'I0045', 1);
INSERT INTO FACULTY_COURSE VALUES ('ECO 201', 'I0045', 1);
INSERT INTO FACULTY_COURSE VALUES ('PED 401', 'I0153', 0);
INSERT INTO FACULTY_COURSE VALUES ('COE 756', 'I0013',1);
INSERT INTO FACULTY_COURSE VALUES ('ELE 302', 'I0013',1);
INSERT INTO FACULTY_COURSE VALUES ('MED 666', 'I0034', 1);


INSERT INTO PROGRAM_COURSE VALUES ('COE 593','B.E.COE');
INSERT INTO PROGRAM_COURSE VALUES ('COE 418','B.E.COE');
INSERT INTO PROGRAM_COURSE VALUES ('MTH 207','B.E.COE');
INSERT INTO PROGRAM_COURSE VALUES ('MTH 207','B.S.CSC');
INSERT INTO PROGRAM_COURSE VALUES ('MTH 207','Min.MTH');
INSERT INTO PROGRAM_COURSE VALUES ('ENG 202','B.E.COE');
INSERT INTO PROGRAM_COURSE VALUES ('MKT 201','B.S.MKT');
INSERT INTO PROGRAM_COURSE VALUES ('MKT 201','B.E.COE');
INSERT INTO PROGRAM_COURSE VALUES ('PED 401','B.E.COE');
INSERT INTO PROGRAM_COURSE VALUES ('PED 401','B.S.MKT');
INSERT INTO PROGRAM_COURSE VALUES ('ELE 302','B.E.COE');
INSERT INTO PROGRAM_COURSE VALUES ('COE 756','M.S.COE');
INSERT INTO PROGRAM_COURSE VALUES ('ECO 201','B.S.MKT');
INSERT INTO PROGRAM_COURSE VALUES ('ENG 101','B.S.MKT');
INSERT INTO PROGRAM_COURSE VALUES ('COE 321','B.E.COE');
INSERT INTO PROGRAM_COURSE VALUES ('COE 312','B.E.COE');
INSERT INTO PROGRAM_COURSE VALUES ('MED 666','M.D. PHD');


INSERT INTO STUDENT_COURSE VALUES ('COE 593', 'S0001');
INSERT INTO STUDENT_COURSE VALUES ('COE 418', 'S0001');
INSERT INTO STUDENT_COURSE VALUES ('MTH 207', 'S0001');
INSERT INTO STUDENT_COURSE VALUES ('ENG 202', 'S0001');
INSERT INTO STUDENT_COURSE VALUES ('COE 418', 'S0002');
INSERT INTO STUDENT_COURSE VALUES ('MKT 201', 'S0002');
INSERT INTO STUDENT_COURSE VALUES ('PED 401', 'S0002');
INSERT INTO STUDENT_COURSE VALUES ('ELE 302', 'S0002');
INSERT INTO STUDENT_COURSE VALUES ('COE 756', 'S0015');
INSERT INTO STUDENT_COURSE VALUES ('COE 418', 'S0015');
INSERT INTO STUDENT_COURSE VALUES ('ELE 302', 'S0015');
INSERT INTO STUDENT_COURSE VALUES ('MTH 207', 'S0015');
INSERT INTO STUDENT_COURSE VALUES ('MKT 201', 'S0037');
INSERT INTO STUDENT_COURSE VALUES ('ECO 201', 'S0037');
INSERT INTO STUDENT_COURSE VALUES ('ENG 101', 'S0037');
INSERT INTO STUDENT_COURSE VALUES ('PED 401', 'S0037');
INSERT INTO STUDENT_COURSE VALUES ('COE 321', 'S0044');
INSERT INTO STUDENT_COURSE VALUES ('COE 312', 'S0044');
INSERT INTO STUDENT_COURSE VALUES ('ELE 302', 'S0044');
INSERT INTO STUDENT_COURSE VALUES ('MTH 207', 'S0044');
INSERT INTO STUDENT_COURSE VALUES ('MED 666', 'S0023');



INSERT INTO STUDENT_STAFF VALUES( 'S0001' , 'ST0002' , 'SPRING' , 2016 , 25.7 , NULL , 'Basil Building ');
INSERT INTO STUDENT_STAFF VALUES( 'S0002' , 'ST031' , 'FALL' , 2014 , 22 , NULL , 'Salida Building' );
INSERT INTO STUDENT_STAFF VALUES( 'S0037' , 'ST0001' , 'WINTER' , 2015 , NULL , 300 , 'Computer Lab' );
INSERT INTO STUDENT_STAFF VALUES( 'S0015' , 'ST133' , 'SUMMER' , 2015 , 15 , NULL , 'Health Center' );
INSERT INTO STUDENT_STAFF VALUES( 'S0023' , 'ST0002' , 'SPRING' , 2016 , 10 , 200 , 'Engineering Lab' );
INSERT INTO STUDENT_STAFF VALUES( 'S0044' , 'ST0001' , 'WINTER' , 2013 , NULL , 300 , 'IT Help Center' );


INSERT INTO STUDENT_SECTION VALUE ('S0001' , 201611 , 'In Progress') ;
INSERT INTO STUDENT_SECTION VALUE ('S0001' , 201621 , 'In Progress') ;
INSERT INTO STUDENT_SECTION VALUE ('S0001' , 201511 , 'A') ;
INSERT INTO STUDENT_SECTION VALUE ('S0001' , 201531 , 'B') ;
INSERT INTO STUDENT_SECTION VALUE ('S0002' , 201621 , 'In Progress') ;
INSERT INTO STUDENT_SECTION VALUE ('S0002' , 201631 , 'In Progress') ;
INSERT INTO STUDENT_SECTION VALUE ('S0002' , 201311 , 'A') ;
INSERT INTO STUDENT_SECTION VALUE ('S0002' , 201541 , 'B+') ;
INSERT INTO STUDENT_SECTION VALUE ('S0015' , 201651 , 'In Progress') ;
INSERT INTO STUDENT_SECTION VALUE ('S0015' , 201622 , 'In Progress') ;
INSERT INTO STUDENT_SECTION VALUE ('S0015' , 201541 , 'B') ;
INSERT INTO STUDENT_SECTION VALUE ('S0015' , 201411 , 'A') ;
INSERT INTO STUDENT_SECTION VALUE ('S0037' , 201631 , 'In Progress') ;
INSERT INTO STUDENT_SECTION VALUE ('S0037' , 201671 , 'In Progress') ;
INSERT INTO STUDENT_SECTION VALUE ('S0037' , 201561 , 'B+') ;
INSERT INTO STUDENT_SECTION VALUE ('S0037' , 201641 , 'A') ;
INSERT INTO STUDENT_SECTION VALUE ('S0044' , 201681 , 'In Progress') ;
INSERT INTO STUDENT_SECTION VALUE ('S0044' , 201691 , 'In Progress') ;
INSERT INTO STUDENT_SECTION VALUE ('S0044' , 201551 , 'A') ;
INSERT INTO STUDENT_SECTION VALUE ('S0044' , 201521 , 'C') ;
INSERT INTO STUDENT_SECTION VALUE ('S0023' , 2016101 , 'In Progress') ;

