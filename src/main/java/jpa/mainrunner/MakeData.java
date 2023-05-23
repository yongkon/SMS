package jpa.mainrunner;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.TypedQuery;
import java.util.List;

public class MakeData {

    public void createTable() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        //Declare two entity(Table)
        Student student = new Student();
        Course course = new Course();

        t.commit();
        System.out.println("database successfully crated");
        factory.close();
        session.close();
    }

    public void insertData() {

        if (!existData()) {

            SessionFactory factory = new Configuration().configure().buildSessionFactory();
            Session session = factory.openSession();
            Transaction t = session.beginTransaction();

            // Insert Data to Student Table
            Student studentOne = new Student("hluckham0@google.ru", "Hazel Luckham", "X1uZcoIh0dj");
            Student studentTwo = new Student("sbowden1@yellowbook.com", "Sonnnie Bowden", "SJc4aWSU");
            Student studentThree = new Student("qllorens2@howstuffworks.com", "Quillan Llorens", "W6rJuxd");
            Student studentFour = new Student("cjaulme9@bing.com", "Cahra Jaulme", "FnVklVgC6r6");
            Student studentFive = new Student("cstartin3@flickr.com", "Clem Startin", "XYHzJ1S");
            Student studentSix = new Student("tattwool4@biglobe.ne.jp", "Thornie Attwool", "Hjt0SoVmuBz");
            Student studentSeven = new Student("hguerre5@deviantart.com", "Harcourt Guerre", "OzcxzD1PGs");
            Student studentEight = new Student("htaffley6@columbia.edu", "Holmes Taffley", "xowtOQ");
            Student studentNine = new Student("aiannitti7@is.gd", "Alexandra Iannitti", "TWP4hf5j");
            Student studentTen = new Student("ljiroudek8@sitemeter.com", "Laryssa Jiroudek", "bXRoLUP");
            session.persist(studentOne);
            session.persist(studentTwo);
            session.persist(studentThree);
            session.persist(studentFour);
            session.persist(studentFive);
            session.persist(studentSix);
            session.persist(studentSeven);
            session.persist(studentEight);
            session.persist(studentNine);
            session.persist(studentTen);

            // Insert Data to Course Table
            Course courseOne = new Course("English", "Anderea Scamaden");
            Course courseTwo = new Course("Mathematics", "Eustace Niemetz");
            Course courseThree = new Course("Anatomy", "Reynolds Pastor");
            Course courseFour = new Course("Organic Chemistry", "Odessa Belcher");
            Course courseFive = new Course("Physics", "Dani Swallow");
            Course courseSix = new Course("Digital Logic", "Glenden Reilingen");
            Course courseSeven = new Course("Object Oriented Programming", "Giselle Ardy");
            Course courseEight = new Course("Data Structures", "Carolan Stoller");
            Course courseNine = new Course("Politics", "Carmita De Maine");
            Course courseTen = new Course("Art", "Kingsly Doxsey");

            session.persist(courseOne);
            session.persist(courseTwo);
            session.persist(courseThree);
            session.persist(courseFour);
            session.persist(courseFive);
            session.persist(courseSix);
            session.persist(courseSeven);
            session.persist(courseEight);
            session.persist(courseNine);
            session.persist(courseTen);

            t.commit();

            System.out.println("successfully saved");
            factory.close();
            session.close();
        }
    }

    public boolean existData() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        String hql = "FROM Student";
        TypedQuery query = session.createQuery(hql);
        List<Student> results = query.getResultList();
        if (results.isEmpty())
            return false;
        else
            return true;
    }
}
