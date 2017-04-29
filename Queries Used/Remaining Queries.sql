STUDENT Register in selected section : 

Conditionstatement:
(SELECT COUNT (*) FROM STUDENT_SECTION WHERE crn = selectedcrn) < (SELECT capacity FROM SECTION WHERE crn = selectedcrn)

Querystatement:
INSERT INTO STUDENT_SECTION VALUES (currentstudentID, selectedcrn, null);



STUDENT Drop selected section : 

DELETE FROM STUDENT_SECTION WHERE studentID=currentstudentID AND crn = selectedcrn;


FACULTY View Students in selected section:
SELECT * FROM STUDENT_SECTION JOIN STUDENT on crn = selectedcrn AND student.studentID = student_section.studentID;