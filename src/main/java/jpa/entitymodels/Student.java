package jpa.entitymodels;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table
public class Student {
    @Id
    @Column (length=50)
    private String email;
    @Column (length=50)
    private String name;
    @Column (length=50)
    private String password;

    @ManyToMany(targetEntity = Course.class, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "email"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List sCourses;

    public Student() {
    }

    public Student(String sEmail, String sName, String sPass) {
        this.email = sEmail;
        this.name = sName;
        this.password = sPass;
    }

    public String getsEmail() {
        return email;
    }

    public void setsEmail(String sEmail) {
        this.email = sEmail;
    }

    public String getsName() {
        return name;
    }

    public void setsName(String sName) {
        this.name = sName;
    }

    public String getsPass() {
        return password;
    }

    public void setsPass(String sPass) {
        this.password = sPass;
    }

    public List getsCourses() {
        return sCourses;
    }

    public void setsCourses(List sCourses) {
        this.sCourses = sCourses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Student other = (Student) o;
        return Objects.equals(email, other.email) && Objects.equals(name, other.name) && Objects.equals(password, other.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, password);
    }
}
