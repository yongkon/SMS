<h2>School Management System</h2>

<h3>1: Tables </h3>
<h3>Table 1 – Student table:</h3>

|Datatype    |  Name    |  Description  |
|:------|:---|:---|
|varchar(50) not null (PK) | email | Student’s current school email, unique student identifier |
|varchar(50) not null | name | The full name of the student |
|varchar(50) not null | password | Student’s password in order to log in |

<h3>Table 2 – Course table:</h3>

|Datatype    |  Name    |  Description  |
|:------|:---|:---|
|int not null (PK) | id | Unique Course Identifier |
|varchar(50) not null | name | Provides the name of the course |
|varchar(50) not null | Instructor | Provides the name of the instructor |

<h3>Requirement 2: Data Access Object (DAO) </h3>
<pre>
<h4>  StudentDAO (Interface) </h4>
  ● getAllStudents();
  ● getStudentByEmail();
  ● validateStudent();
  ● registerStudentToCourse();
  ● getStudentCourses();
 <h4>   CourseDAO (Interface) </h4>
  ● getAllCourses();
</pre>

  StudentService and CourseService which implements the respective DAOs
  
 | No. | Return Type | Class Name | Method Name | Input Parameters |
 |--|------------|------------|-------------|-------------------|
 | 1 | List<Student> | StudentService | getAllStudents <br> –This method reads the student table in your database and returns the data as a List<Student> | None |
 | 2 | Student | StudentService | getStudentByEmail <br> –This method takes a Student’s email as a String and parses the student list for a Student with that email and returns a Student Object. | String sEmail |
 | 3 | boolean | StudentService | validateStudent <br> –This method takes two parameters: the first one is the user email and the second one is the password from the user input. Return whether or not a student was found. | String sEmail, String sPassword |
 | 4 | void | StudentService | registerStudentToCourse <br>  –After a successful student validation, this method takes a Student’s email and a Course ID. It checks in the join table (i.e. Student_Course) generated by JPA to find if a Student with that Email is currently attending a Course with that ID. If the Student is not attending that Course, register the student for that course; otherwise not. | String sEmail, int cId |
 | 5 | List<Course> | StudentService | getStudentCourses <br> –This method takes a Student’s Email as a parameter and would find all the courses a student is registered for. | String sEmail |
 | 6 | List<Course> | CourseService | getAllCourses <br> –This method takes no parameters and returns every Course in the table. | None |
