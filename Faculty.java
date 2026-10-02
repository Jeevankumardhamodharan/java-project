package model;

import interfaces.Reportable;

/**
 * Represents a Faculty member. Extends Person (Inheritance) and
 * implements Reportable (Polymorphism via interface).
 */
public class Faculty extends Person implements Reportable {

    private static final long serialVersionUID = 1L;

    private String facultyId;
    private String department;
    private String designation;

    public Faculty(String id, String name, String email, String password,
                    String facultyId, String department, String designation) {
        super(id, name, email, password);
        this.facultyId = facultyId;
        this.department = department;
        this.designation = designation;
    }

    // Overloaded constructor with a sensible default designation.
    // Demonstrates Constructor Overloading.
    public Faculty(String id, String name, String email, String password,
                    String facultyId, String department) {
        this(id, name, email, password, facultyId, department, "Assistant Professor");
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String displayDetails() {
        return "Faculty ID   : " + getId() + "\n" +
               "Name         : " + getName() + "\n" +
               "Email        : " + getEmail() + "\n" +
               "Faculty Code : " + facultyId + "\n" +
               "Department   : " + department + "\n" +
               "Designation  : " + designation;
    }

    @Override
    public String generateSummary() {
        return facultyId + " - " + getName() + " (" + department + ")";
    }
}
