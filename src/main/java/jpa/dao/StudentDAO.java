package jpa.dao;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;

import java.util.List;

public interface StudentDAO {
    List<Student> getAllStudents();
    Student getStudentByEmail(String email);

    Boolean validateStudent(String email, String password);

    Boolean registerStudentToCourse(String email, int cId);

    List<Course> getStudentCourses(String email);
}
