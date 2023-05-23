package jpa.service;

import jpa.entitymodels.Course;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CourseServiceTest {
    private Connection connection;
    private CourseService courseService;
    @BeforeAll
    public void setUp() throws SQLException {
        // Establish the database connection
        String url = "jdbc:mysql://localhost:3306/schoolMS";
        String username = "root";
        String password = "password";
        connection = DriverManager.getConnection(url, username, password);

        courseService = new CourseService();
    }

    @Test
    void getAllCourses() throws SQLException {
        // Test getAllCourses method
        List<Course> courseList = courseService.getAllCourses();

        // Test using database value
        List<Course> checkList = new ArrayList<Course>();

        // Select All courses from Course table
        PreparedStatement stmt = connection.prepareStatement("Select * From Course");
        ResultSet rs = stmt.executeQuery();
        checkList.clear();
        while(rs.next()) {
             checkList.add(new Course(rs.getInt("id"), rs.getString("name"), rs.getString("instructorName")));
        }
        assertEquals(checkList, courseList);
    }

    @AfterAll
    public void tearDown() throws SQLException {
        // Close the database connection
        if (connection != null) {
            connection.close();
        }
    }
}
