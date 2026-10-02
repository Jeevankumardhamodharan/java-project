package service;

import enums.UserRole;
import exception.InvalidInputException;
import exception.UserNotFoundException;
import model.Admin;
import model.Faculty;
import model.Person;
import model.Student;
import repository.FacultyRepository;
import repository.StudentRepository;
import util.ValidationUtil;

import java.util.Optional;

/**
 * Central Authentication Module. Handles login for Admin, Faculty,
 * and Student, plus password validation. A single hardcoded Admin
 * account is provisioned in-memory (default credentials below) since
 * the 40%-scope menu has no "Register Admin" feature — only Admins
 * that already exist should be able to log in.
 */
public class AuthService {

    // Default administrator account. In a real system this would be
    // provisioned via a secure setup step; here it is hardcoded for
    // simplicity, as required by the console-only, no-database scope.
    private static final Admin DEFAULT_ADMIN =
            new Admin("ADM0001", "System Administrator", "admin@examvision.com", "admin123", "ADM0001");

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public AuthService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    /**
     * Validates raw login input before any lookup is attempted.
     */
    public void validateLoginInput(String username, String password) throws InvalidInputException {
        ValidationUtil.validateNotEmpty(username, "Username/Email");
        ValidationUtil.validateNotEmpty(password, "Password");
    }

    /**
     * Authenticates an Administrator using email + password.
     */
    public Admin loginAdmin(String email, String password) throws UserNotFoundException {
        if (DEFAULT_ADMIN.getEmail().equalsIgnoreCase(email) && DEFAULT_ADMIN.checkPassword(password)) {
            return DEFAULT_ADMIN;
        }
        throw new UserNotFoundException("Invalid administrator credentials.");
    }

    /**
     * Authenticates a Faculty member using email + password.
     */
    public Faculty loginFaculty(String email, String password) throws UserNotFoundException {
        Optional<Faculty> match = facultyRepository.findByEmail(email);
        if (match.isPresent() && match.get().checkPassword(password)) {
            return match.get();
        }
        throw new UserNotFoundException("Invalid faculty credentials, or no such faculty account exists.");
    }

    /**
     * Authenticates a Student using email + password.
     */
    public Student loginStudent(String email, String password) throws UserNotFoundException {
        Optional<Student> match = studentRepository.findByEmail(email);
        if (match.isPresent() && match.get().checkPassword(password)) {
            return match.get();
        }
        throw new UserNotFoundException("Invalid student credentials, or no such student account exists.");
    }

    /**
     * Overloaded convenience method: authenticate by supplying a
     * UserRole directly, useful if the menu is later refactored to
     * be role-generic. Demonstrates Method Overloading + use of enum.
     */
    public Person loginByRole(UserRole role, String email, String password) throws UserNotFoundException {
        switch (role) {
            case ADMIN:
                return loginAdmin(email, password);
            case FACULTY:
                return loginFaculty(email, password);
            case STUDENT:
                return loginStudent(email, password);
            default:
                throw new UserNotFoundException("Unknown role.");
        }
    }
}
