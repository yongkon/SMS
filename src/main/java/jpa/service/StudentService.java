package jpa.service;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentService implements StudentDAO {
    @Override
    public List<Student> getAllStudents() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Query sql = session.createQuery("From Student");
        List<Student> resultList = sql.getResultList();

        factory.close();
        session.close();

        return resultList;
    }

    @Override
    public Student getStudentByEmail(String email) {
        try {
            SessionFactory factory = new Configuration().configure().buildSessionFactory();
            Session session = factory.openSession();
            TypedQuery sql = session.createQuery("From Student Where email = :email");
            sql.setParameter("email", email);
            Student result = (Student) sql.getSingleResult();

            factory.close();
            session.close();

            return result;
        } catch(NoResultException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean validateStudent(String email, String password) {
        Student result = null;
        try {
            SessionFactory factory = new Configuration().configure().buildSessionFactory();
            Session session = factory.openSession();
            TypedQuery sql = session.createQuery("From Student Where email = :email and password = :password");
            sql.setParameter("email", email);
            sql.setParameter("password", password);
            result = (Student) sql.getSingleResult();

            factory.close();
            session.close();
        } catch(NoResultException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return (result != null) ? true : false;
    }

    @Override
    public Boolean registerStudentToCourse(String email, int cId) {
        try {
            SessionFactory factory = new Configuration().configure().buildSessionFactory();
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            //Retrieve student data
            TypedQuery sql = session.createQuery("From Student Where email = :email");
            sql.setParameter("email", email);
            Student student = (Student)sql.getSingleResult();

            //Retrieve course data
            sql = session.createQuery("From Course Where id = :cId");
            sql.setParameter("cId", cId);
            Course course = (Course)sql.getSingleResult();

            //Insert a data to student_course Table
            //Add a course to the student's list of courses
            student.getsCourses().add(course);
            System.out.println(student.getsName());
            System.out.println(course.getcName());
            session.save(student);

            tx.commit();
            factory.close();
            session.close();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();

        String hqlQuery = "SELECT t1.id, t1.instructorName, t1.name " +
                "FROM Course t1 " +
                "JOIN Student_Course t2 ON t1.id = t2.id " +
                "WHERE t2.email = :email " +
                "Order by t1.id";

        List<Object[]> results = session.createNativeQuery(hqlQuery).
                setParameter("email",email).getResultList();

        List<Course> newCourse = new ArrayList<Course>();
        for(Object[] row : results) {
            Course c = new Course();
            c.setcId((Integer)row[0]);
            c.setcInstructorName((String)row[1]);
            c.setcName((String)row[2]);

            newCourse.add(c);
        }
        factory.close();
        session.close();

        return  newCourse;
    }
}
