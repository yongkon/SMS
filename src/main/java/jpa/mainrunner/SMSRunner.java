package jpa.mainrunner;

import jpa.entitymodels.Course;
import jpa.service.CourseService;
import jpa.entitymodels.Student;
import jpa.service.StudentService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class SMSRunner {

    private Scanner sin;
    private StringBuilder sb;

    private CourseService courseService;
    private StudentService studentService;
    private Student currentStudent;

    SMSRunner() {
        sin = new Scanner(System.in);
        sb = new StringBuilder();

        courseService = new CourseService();
        studentService = new StudentService();
    }

    public static void main(String[] args) {

        //Data Object for MySQL Database
        MakeData newData = new MakeData();

        // Create Database and Tables (Course, Student)
        newData.createTable();

        // Insert Data to Student, Course tables
        newData.insertData();

        SMSRunner sms = new SMSRunner();
        sms.run();
    }

    public void run() {
        // Login or quit
        boolean runLoop = true;

        while(runLoop) {
            int i = menu1();
            // Choose 1 :Student login
            if (i == 1) {
                studentLogin();
            // Choose 2 : Quit Application
            } else if (i == 2) {
                runLoop = false;
            // Wrong menu input
            } else {
                System.out.println("*** Wrong input. Please check your input. ***");
            }
        }
        System.out.println("*** Goodbye! ***");
    }

    public int menu1() {
        sb.append("\n1. Student Login\n2. Quit Application\nPlease Enter Selection: ");
        System.out.print(sb.toString());
        sb.delete(0, sb.length());

        while(!sin.hasNextInt()) {
            sin.next();
            System.out.println("Please enter the number (1 or 2)");
        }
        return sin.nextInt();
    }

    private void studentLogin() {

        boolean loop = true;

        // Input the login information (email & password)
        System.out.print("Enter your email address: ");
        String email = sin.next();
        System.out.print("Enter your password: ");
        String password = sin.next();

        //Retrieve Student information by Email
        currentStudent = studentService.getStudentByEmail(email);

        //Check the student's credential (check the password)
        if (currentStudent != null && currentStudent.getsPass().equals(password)) {
            while (registerMenu(email)) {
                //Do the registerMenu until logout
            }
        } else {
            // Wrong password
            System.out.println("*** User Validation failed. ***");
        }
    }

    private boolean registerMenu(String email) {
        //Retrieve all student's personal courses
        List<Course> courses = studentService.getStudentCourses(email);
        System.out.println(currentStudent.getsName() + "'s Classes:");
        printMyCourses(courses);

        boolean loop = true;
        sb.append("\n1. Register a class\n2. Logout\nPlease Enter Selection: ");
        System.out.print(sb.toString());
        sb.delete(0, sb.length());

        while(!sin.hasNextInt()) {
            sin.next();
            System.out.println("Please enter the number (1 or 2)");
        }
        switch (sin.nextInt()) {
            case 1:
                // Register a class
                // Retrieve all classes that student can choose
                String sEmail = currentStudent.getsEmail();
                List<Course> allCourses = courseService.getAllCourses();
                List<Course> studentCourses = studentService.getStudentCourses(sEmail);
                allCourses.removeAll(studentCourses);
                System.out.println("Choose Class:");
                printMyCourses(allCourses);

                // Select Course ID want to take
                System.out.print("Enter Course Number: ");
                int courseNumber = sin.nextInt();

                // Retrieve selected course information
                Course newCourse = getCourseByID(courseNumber);
                if (newCourse != null && checkCourseValidate(courseNumber, studentCourses)) {
                    // if the selected course is available, then register it to the table
                    studentService.registerStudentToCourse(sEmail, newCourse.getcId());

                    // Retrieve updated personal courses
                    //List<Course> studentNewCourses = studentService.getStudentCourses(sEmail);
                    //System.out.println(currentStudent.getsName() + "'s Updated Classes:");
                    //printMyCourses(studentNewCourses);
                } else {
                    // If choose already taking course or not listed course
                    System.out.println("*** Course Validation failed. ***");
                }
                break;
            case 2:
                // Select logout
                System.out.println("*** " + currentStudent.getsName() +" Goodbye! ***");
                loop = false;
                break;
            default:
                // Input wrong information
                System.out.println("*** Wrong Input ***");
        }
        return loop;
    }

    void printMyCourses (List<Course> myCourses) {
        System.out.printf("%5s%30S%25s\n", "ID", "Course", "Instructor");
        for (Course course : myCourses) {
            System.out.printf("%5s%30S%25s\n", course.getcId(), course.getcName(), course.getcInstructorName());
        }
    }

    boolean checkCourseValidate(int cid, List<Course> courses) {
        boolean letVal = true;
        // If choose a course already taking then return the process
        for(Course c : courses) {
            if (c.getcId() == cid) {
                letVal = false;
                System.out.println("You are already registered in that course!");
                return letVal;
            }
        }
        return letVal;
    }
    Course getCourseByID(int cid) {
        try {
            SessionFactory factory = new Configuration().configure().buildSessionFactory();
            Session session = factory.openSession();
            Query sql = session.createQuery("From Course Where id = :id");
            sql.setParameter("id", cid);

            Course course = (Course) sql.getSingleResult();

            factory.close();
            session.close();

            return course;
        } catch(NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }
}