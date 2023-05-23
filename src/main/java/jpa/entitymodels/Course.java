package jpa.entitymodels;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length=50)
    private String name;

    @Column(length=50)
    private String instructorName;

    public Course() {
    }

    public Course(String cName, String cInstructorName) {
        this.name = cName;
        this.instructorName = cInstructorName;
    }

    public Course(int cid, String cName, String cInstructorName) {
        this.id = cid;
        this.name = cName;
        this.instructorName = cInstructorName;
    }

    public int getcId() {
        return id;
    }

    public void setcId(int cId) {
        this.id = cId;
    }

    public String getcName() {
        return this.name;
    }

    public void setcName(String cName) {
        this.name = cName;
    }

    public String getcInstructorName() {
        return this.instructorName;
    }

    public void setcInstructorName(String cInstructorName) {
        this.instructorName = cInstructorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Course other = (Course) o;
        return id == other.id && Objects.equals(name, other.name)  && Objects.equals(instructorName, other.instructorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, instructorName);
    }
}
