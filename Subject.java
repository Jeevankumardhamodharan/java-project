package model;

import java.io.Serializable;

/**
 * Represents an academic subject that an Exam can be conducted for.
 * Kept intentionally simple in this phase (no separate SubjectRepository
 * is required by the 40% scope) — Subject objects are created inline
 * when an Admin creates an Exam, and persisted as part of that Exam
 * via Serialization.
 */
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    private String subjectCode;
    private String subjectName;
    private int credits;

    public Subject(String subjectCode, String subjectName, int credits) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credits = credits;
    }

    // Overloaded constructor: allows creating a Subject without
    // specifying credits (defaults to 0). Demonstrates Constructor Overloading.
    public Subject(String subjectCode, String subjectName) {
        this(subjectCode, subjectName, 0);
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return subjectName + " (" + subjectCode + ")";
    }
}
