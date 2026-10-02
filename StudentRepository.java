package repository;

import exception.UserNotFoundException;
import model.Student;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Handles persistence and in-memory management of Student records.
 * Backed by Serialization via FileUtil (students.dat).
 * Loads all data into memory once on construction and re-saves
 * after every mutating operation, which is simple and adequate
 * for a console-based, single-user academic project.
 */
public class StudentRepository {

    private static final String FILE_NAME = "students.dat";

    private List<Student> students;

    public StudentRepository() {
        this.students = FileUtil.loadList(FILE_NAME);
    }

    private void persist() {
        FileUtil.saveList(FILE_NAME, students);
    }

    public void addStudent(Student student) {
        students.add(student);
        persist();
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public int count() {
        return students.size();
    }

    /**
     * Searches by roll number (case-insensitive). Overloaded below to
     * also support searching by system-generated ID.
     */
    public Optional<Student> findByRollNumber(String rollNumber) {
        return students.stream()
                .filter(s -> s.getRollNumber().equalsIgnoreCase(rollNumber))
                .findFirst();
    }

    // Overloaded search method: search by internal Student ID instead
    // of roll number. Demonstrates Method Overloading.
    public Optional<Student> findById(String id) {
        return students.stream()
                .filter(s -> s.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    public Optional<Student> findByEmail(String email) {
        return students.stream()
                .filter(s -> s.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    /**
     * Deletes a student by roll number.
     * Throws UserNotFoundException if no matching student exists.
     */
    public void deleteByRollNumber(String rollNumber) throws UserNotFoundException {
        Student target = findByRollNumber(rollNumber)
                .orElseThrow(() -> new UserNotFoundException(
                        "No student found with roll number: " + rollNumber));
        students.remove(target);
        persist();
    }
}
