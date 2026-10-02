package main;

import enums.ExamStatus;
import exception.InvalidInputException;
import exception.UserNotFoundException;
import model.*;
import repository.ExamRepository;
import repository.FacultyRepository;
import repository.StudentRepository;
import service.AuthService;
import util.DateUtil;
import util.IDGenerator;
import util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point of the ExamVision console application.
 * Wires together the repository, service, and util layers and drives
 * the entire menu system using Scanner. This is Phase 1 (40% scope):
 * Login/registration + the specific Admin/Faculty/Student features
 * listed in the project brief only.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final StudentRepository studentRepository = new StudentRepository();
    private static final FacultyRepository facultyRepository = new FacultyRepository();
    private static final ExamRepository examRepository = new ExamRepository();
    private static final AuthService authService = new AuthService(studentRepository, facultyRepository);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    handleAdminLogin();
                    break;
                case "2":
                    handleFacultyLogin();
                    break;
                case "3":
                    handleStudentEntry();
                    break;
                case "4":
                    running = false;
                    System.out.println("\nThank you for using ExamVision. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-4.\n");
            }
        }
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n==============================");
        System.out.println("        EXAMVISION");
        System.out.println("==============================");
        System.out.println("1. Administrator Login");
        System.out.println("2. Faculty Login");
        System.out.println("3. Student Login");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    // ======================================================
    // ADMIN
    // ======================================================

    private static void handleAdminLogin() {
        System.out.print("\nEmail: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            authService.validateLoginInput(email, password);
            Admin admin = authService.loginAdmin(email, password);
            System.out.println("\nLogin successful. Welcome, " + admin.getName() + "!");
            adminMenu();
        } catch (InvalidInputException | UserNotFoundException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    private static void adminMenu() {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n----- ADMIN MENU -----");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Add Faculty");
            System.out.println("6. View Faculty");
            System.out.println("7. Create Exam");
            System.out.println("8. View Exams");
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": addStudent(); break;
                case "2": viewStudents(); break;
                case "3": searchStudent(); break;
                case "4": deleteStudent(); break;
                case "5": addFaculty(); break;
                case "6": viewFaculty(); break;
                case "7": createExam(); break;
                case "8": viewExams(); break;
                case "9":
                    loggedIn = false;
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-9.");
            }
        }
    }

    private static void addStudent() {
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine();
            ValidationUtil.validateNotEmpty(name, "Name");

            System.out.print("Email: ");
            String email = scanner.nextLine();
            ValidationUtil.validateEmail(email);

            System.out.print("Password: ");
            String password = scanner.nextLine();
            ValidationUtil.validatePassword(password);

            System.out.print("Roll Number: ");
            String rollNumber = scanner.nextLine();
            ValidationUtil.validateAlphanumeric(rollNumber, "Roll Number");

            System.out.print("Department: ");
            String department = scanner.nextLine();
            ValidationUtil.validateNotEmpty(department, "Department");

            System.out.print("Semester: ");
            int semester = readInt();
            ValidationUtil.validatePositiveInt(semester, "Semester");

            String id = IDGenerator.generateStudentId(studentRepository.count());
            Student student = new Student(id, name, email, password, rollNumber, department, semester);
            studentRepository.addStudent(student);

            System.out.println("Student added successfully with ID: " + id);
        } catch (InvalidInputException e) {
            System.out.println("Could not add student: " + e.getMessage());
        }
    }

    private static void viewStudents() {
        List<Student> students = studentRepository.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("\n----- ALL STUDENTS -----");
        for (Student s : students) {
            System.out.println("---------------------------");
            System.out.println(s.displayDetails());
        }
    }

    private static void searchStudent() {
        System.out.print("Enter Roll Number to search: ");
        String rollNumber = scanner.nextLine();
        studentRepository.findByRollNumber(rollNumber)
                .ifPresentOrElse(
                        s -> {
                            System.out.println("\nStudent found:");
                            System.out.println(s.displayDetails());
                        },
                        () -> System.out.println("No student found with roll number: " + rollNumber));
    }

    private static void deleteStudent() {
        System.out.print("Enter Roll Number to delete: ");
        String rollNumber = scanner.nextLine();
        try {
            studentRepository.deleteByRollNumber(rollNumber);
            System.out.println("Student deleted successfully.");
        } catch (UserNotFoundException e) {
            System.out.println("Could not delete: " + e.getMessage());
        }
    }

    private static void addFaculty() {
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine();
            ValidationUtil.validateNotEmpty(name, "Name");

            System.out.print("Email: ");
            String email = scanner.nextLine();
            ValidationUtil.validateEmail(email);

            System.out.print("Password: ");
            String password = scanner.nextLine();
            ValidationUtil.validatePassword(password);

            System.out.print("Department: ");
            String department = scanner.nextLine();
            ValidationUtil.validateNotEmpty(department, "Department");

            String facultyId = IDGenerator.generateFacultyId(facultyRepository.count());
            String id = facultyId;
            Faculty faculty = new Faculty(id, name, email, password, facultyId, department);
            facultyRepository.addFaculty(faculty);

            System.out.println("Faculty added successfully with ID: " + facultyId);
        } catch (InvalidInputException e) {
            System.out.println("Could not add faculty: " + e.getMessage());
        }
    }

    private static void viewFaculty() {
        List<Faculty> facultyList = facultyRepository.getAllFaculty();
        if (facultyList.isEmpty()) {
            System.out.println("No faculty found.");
            return;
        }
        System.out.println("\n----- ALL FACULTY -----");
        for (Faculty f : facultyList) {
            System.out.println("---------------------------");
            System.out.println(f.displayDetails());
        }
    }

    private static void createExam() {
        try {
            System.out.print("Subject Code: ");
            String subjectCode = scanner.nextLine();
            ValidationUtil.validateNotEmpty(subjectCode, "Subject Code");

            System.out.print("Subject Name: ");
            String subjectName = scanner.nextLine();
            ValidationUtil.validateNotEmpty(subjectName, "Subject Name");
            Subject subject = new Subject(subjectCode, subjectName);

            System.out.print("Hall Name: ");
            String hallName = scanner.nextLine();
            ValidationUtil.validateNotEmpty(hallName, "Hall Name");

            System.out.print("Hall Capacity: ");
            int capacity = readInt();
            ValidationUtil.validatePositiveInt(capacity, "Hall Capacity");
            Hall hall = new Hall(IDGenerator.generateId("HAL", 0), hallName, capacity);

            System.out.print("Exam Date (dd-MM-yyyy): ");
            LocalDate examDate = DateUtil.parseDate(scanner.nextLine());
            if (!DateUtil.isTodayOrFuture(examDate)) {
                throw new InvalidInputException("Exam date cannot be in the past.");
            }

            System.out.print("Start Time (HH:mm): ");
            LocalTime startTime = DateUtil.parseTime(scanner.nextLine());

            System.out.print("Duration (minutes): ");
            int duration = readInt();
            ValidationUtil.validatePositiveInt(duration, "Duration");

            System.out.print("Assign Faculty ID (leave blank if none yet): ");
            String facultyId = scanner.nextLine().trim();
            facultyId = facultyId.isEmpty() ? null : facultyId;

            String examId = IDGenerator.generateExamId(examRepository.count());
            Exam exam = new Exam(examId, subject, hall, examDate, startTime, duration, facultyId);
            examRepository.addExam(exam);

            System.out.println("Exam created successfully with ID: " + examId);
        } catch (InvalidInputException e) {
            System.out.println("Could not create exam: " + e.getMessage());
        }
    }

    private static void viewExams() {
        List<Exam> exams = examRepository.getAllExams();
        if (exams.isEmpty()) {
            System.out.println("No exams found.");
            return;
        }
        System.out.println("\n----- ALL EXAMS -----");
        for (Exam e : exams) {
            System.out.println(e.generateSummary());
        }
    }

    // ======================================================
    // FACULTY
    // ======================================================

    private static void handleFacultyLogin() {
        System.out.print("\nEmail: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            authService.validateLoginInput(email, password);
            Faculty faculty = authService.loginFaculty(email, password);
            System.out.println("\nLogin successful. Welcome, " + faculty.getName() + "!");
            facultyMenu(faculty);
        } catch (InvalidInputException | UserNotFoundException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    private static void facultyMenu(Faculty faculty) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n----- FACULTY MENU -----");
            System.out.println("1. View Assigned Exams");
            System.out.println("2. View Student List");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": viewAssignedExams(faculty); break;
                case "2": viewStudents(); break;
                case "3":
                    loggedIn = false;
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-3.");
            }
        }
    }

    private static void viewAssignedExams(Faculty faculty) {
        List<Exam> assigned = examRepository.findByFacultyId(faculty.getFacultyId());
        if (assigned.isEmpty()) {
            System.out.println("No exams currently assigned to you.");
            return;
        }
        System.out.println("\n----- YOUR ASSIGNED EXAMS -----");
        for (Exam e : assigned) {
            System.out.println(e.generateSummary());
        }
    }

    // ======================================================
    // STUDENT
    // ======================================================

    private static void handleStudentEntry() {
        System.out.println("\n1. Register");
        System.out.println("2. Login");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            registerStudent();
        } else if (choice.equals("2")) {
            handleStudentLogin();
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void registerStudent() {
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine();
            ValidationUtil.validateNotEmpty(name, "Name");

            System.out.print("Email: ");
            String email = scanner.nextLine();
            ValidationUtil.validateEmail(email);
            if (studentRepository.findByEmail(email).isPresent()) {
                throw new InvalidInputException("An account with this email already exists.");
            }

            System.out.print("Password: ");
            String password = scanner.nextLine();
            ValidationUtil.validatePassword(password);

            System.out.print("Roll Number: ");
            String rollNumber = scanner.nextLine();
            ValidationUtil.validateAlphanumeric(rollNumber, "Roll Number");

            System.out.print("Department: ");
            String department = scanner.nextLine();
            ValidationUtil.validateNotEmpty(department, "Department");

            System.out.print("Semester: ");
            int semester = readInt();
            ValidationUtil.validatePositiveInt(semester, "Semester");

            String id = IDGenerator.generateStudentId(studentRepository.count());
            Student student = new Student(id, name, email, password, rollNumber, department, semester);
            studentRepository.addStudent(student);

            System.out.println("Registration successful! Your Student ID is: " + id);
        } catch (InvalidInputException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private static void handleStudentLogin() {
        System.out.print("\nEmail: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            authService.validateLoginInput(email, password);
            Student student = authService.loginStudent(email, password);
            System.out.println("\nLogin successful. Welcome, " + student.getName() + "!");
            studentMenu(student);
        } catch (InvalidInputException | UserNotFoundException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    private static void studentMenu(Student student) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n----- STUDENT MENU -----");
            System.out.println("1. View Profile");
            System.out.println("2. View Exam Schedule");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("\n" + student.displayDetails());
                    break;
                case "2":
                    viewExams();
                    break;
                case "3":
                    loggedIn = false;
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-3.");
            }
        }
    }

    // ======================================================
    // HELPERS
    // ======================================================

    /**
     * Safely reads an integer from Scanner, re-prompting on bad input
     * instead of crashing with a NumberFormatException.
     */
    private static int readInt() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}
