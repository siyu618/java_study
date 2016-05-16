package com.study.guava;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by tianyuzhi on 16/5/12.
 */
public class ObjectsTest {
    public static void main(String[] args) {
        Student s1 = new Student("Mahesh", "Parashar", 1, "VI");
        Student s2 = new Student("Suresh", null, 3, null);
        System.out.println(s1.equals(s2));
        System.out.println(s1.hashCode());
        System.out.println(
                //Objects.toStringHelper()
                MoreObjects.toStringHelper(s1)
                .add("name", s1.getFirstName() + " " + s1.getLastName())
                .add("class", s1.getClassName())
                .add("Roll No", s1.getRollNo())
                .toString()
        );
    }
}


@Getter
@Setter
class Student {
    private String firstName;
    private String lastName;
    private int rollNo;
    private String className;
    public Student(String firstName, String lastName, int rollNo, String className) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.rollNo = rollNo;
        this.className = className;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (!(object instanceof Student)) {
            return false;
        }
        Student student = (Student)object;
        return Objects.equal(firstName, student.firstName)
                && Objects.equal(lastName, student.lastName)
                && Objects.equal(rollNo, student.rollNo)
                && Objects.equal(className, student.className);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(className, rollNo);
    }

}