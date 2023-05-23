package jpa.service;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentServiceTest {
    private Connection connection;
    private StudentService studentService;

    @BeforeAll
    public void setUp() throws SQLException {
        // Establish the database connection
        String url = "jdbc:mysql://localhost:3306/schoolMS";
        String username = "root";
        String password = "password";
        connection = DriverManager.getConnection(url, username, password);

        studentService = new StudentService();
    }

    @Test
    void getAllStudents() throws SQLException {
        // Test studentList method
        List<Student> studentList = studentService.getAllStudents();

        // Test using database value
        List<Student> checkList = new ArrayList<Student>();
        PreparedStatement stmt = connection.prepareStatement("Select * From Student");
        ResultSet rs = stmt.executeQuery();

        while(rs.next()) {
            checkList.add(new Student(rs.getString("email"), rs.getString("name"), rs.getString("password")));
        }

        assertEquals(checkList, studentList);
    }

    @Test
    void getStudentByEmail() throws SQLException {
        //email : sbowden1@yellowbook.com	name : Sonnnie Bowden	password: SJc4aWSU
        // Test using database value
        Student student = null;
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Student WHERE email = ?");
        stmt.setString(1, "sbowden1@yellowbook.com");
        ResultSet rs = stmt.executeQuery();
        rs.next();
        student = new Student(rs.getString("email"), rs.getString("name"), rs.getString("password"));

        assertEquals(studentService.getStudentByEmail("sbowden1@yellowbook.com"), student);
    }

    @Test
    void validateStudent() throws SQLException {
        //email : cstartin3@flickr.com	 name : Clem Startin	 password : XYHzJ1S
        String email = "cstartin3@flickr.com";
        String password = "XYHzJ1S";
        String passwordTwo = "W6rJuxd"; // This password for qllorens2@howstuffworks.com

        // Test using two values
        assertEquals(studentService.validateStudent(email, password), true);
        assertEquals(studentService.validateStudent(email, passwordTwo), false);
    }

    @Test
    void registerStudentToCourse() throws SQLException {
        // Delete if email has previous record
        PreparedStatement stmt = connection.prepareStatement("Delete FROM Student_Course WHERE email = ?");
        stmt.setString(1, "ljiroudek8@sitemeter.com");
        int result = stmt.executeUpdate();
        System.out.println("Delete ..." + result);

        // Register test data to the table
        // Student:(email) ljiroudek8@sitemeter.com	(name) Laryssa Jiroudek	 (passwd) bXRoLUP
        // Course :(id) 7	   (instructorName) Giselle Ardy	  (name) Object Oriented Programming
        studentService.registerStudentToCourse("ljiroudek8@sitemeter.com", 7);

        // Retrieve from the database to check
        stmt = connection.prepareStatement("SELECT * FROM Student_Course WHERE email = ?");
        stmt.setString(1, "ljiroudek8@sitemeter.com");
        ResultSet rs = stmt.executeQuery();
        rs.next();

        assertEquals(7, rs.getInt("id"));
    }

    @Test
    void getStudentCourses() throws SQLException {
        // Retrieve Data
        // ljiroudek8@sitemeter.com  >> Object Oriented Programming
        String sqlQuery = "SELECT t1.id, t1.instructorName, t1.name " +
                "FROM Course t1 " +
                "JOIN Student_Course t2 ON t1.id = t2.id " +
                "WHERE t2.email = ? " +
                "Order by t1.id";
        PreparedStatement stmt = connection.prepareStatement(sqlQuery);
        stmt.setString(1, "ljiroudek8@sitemeter.com");
        ResultSet rs = stmt.executeQuery();
        List<Course> checkList = new ArrayList<Course>();
        while(rs.next()) {
            Course course = new Course(rs.getInt("id"), rs.getString("name"), rs.getString("instructorName"));
            checkList.add(course);
        }

        // Test method
        List<Course> studentCourse = studentService.getStudentCourses("ljiroudek8@sitemeter.com");

        assertEquals(checkList, studentCourse);
    }

    @AfterAll
    public void tearDown() throws SQLException {
        // Close the database connection
        if (connection != null) {
            connection.close();
        }
    }
}