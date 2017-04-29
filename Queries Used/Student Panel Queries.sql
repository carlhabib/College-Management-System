Current Section :
 
Select isinstanceof, term , sectionyear from STUDENT_SECTION S , Section se WHERE S.crn = se.crn AND grade = 'In Progress' AND studentID= put student ID here;

Enroll :

SELECT distinct courseID,credits, coursetitle FROM section S , COURSE C where S.isinstanceof = C.courseID and term = 'fall' and sectionyear = 2016 ;

View course section:

select * from section where isinstanceof = selected course




Grades:

Select isinstanceof, term , sectionyear, grade from STUDENT_SECTION S , Section se WHERE S.crn = se.crn AND grade != 'In Progress' AND studentID = Selected student ID ;

Financial Aid:

select * from STUDENT_STAFF where studentID=Selected student ID;

Password:

update student set passwd =  inserted password where studentID= selected student ; 
