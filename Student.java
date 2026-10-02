package model;

import interfaces.Reportable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Student. Extends the abstract Person class (Inheritance)
 * and implements Reportable so a short summary can be generated
 * (Polymorphism via interface implementation).
 */
public class Student extends Person implements Reportable {

    private static final long serialVersionUID = 1L;

    private String rollNumber;
    private String department;
    private int semester;
    private List<String> enrolledSubjectCodes;

    /**
     * Full constructor.
     */
    public Student(String id, String name, String email, String password,
                    String rollNumber, String department, int semester) {
        super(id, name, email, password);
        this.rollNumber = rollNumber;
        this.department = department;
        this.semester = semester;
        this.enrolledSubjectCodes = new ArrayList<>();
    }

    // Overloaded constructor: allows registration flow (Student self-register)
    // where department/semester may be supplied later via setters.
    // Demonstrates Constructor Overloading.
    public Student(String id, String name, String email, String password, String rollNumber) {
        this(id, name, email, password, rollNumber, "Not Assigned", 1);
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public List<String> getEnrolledSubjectCodes() {
        return enrolledSubjectCodes;
    }

    public void enrollInSubject(String subjectCode) {
        if (!enrolledSubjectCodes.contains(subjectCode)) {
            enrolledSubjectCodes.add(subjectCode);
        }
    }

    /**
     * Overridden from Person (Method Overriding / Runtime Polymorphism).
     */
    @Override
    public String displayDetails() {
        return "Student ID   : " + getId() + "\n" +
               "Name         : " + getName() + "\n" +
               "Email        : " + getEmail() + "\n" +
               "Roll Number  : " + rollNumber + "\n" +
               "Department   : " + department + "\n" +
               "Semester     : " + semester;
    }

    /**
     * Implementation of Reportable.
     */
    @Override
    public String generateSummary() {
        return rollNumber + " - " + getName() + " (" + department + ", Sem " + semester + ")";
    }
}
