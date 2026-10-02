package model;

import enums.ExamStatus;
import interfaces.Monitorable;
import interfaces.Reportable;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents an Exam. Implements both Monitorable and Reportable,
 * demonstrating a class implementing multiple interfaces and
 * Polymorphism (an Exam can be treated as a Monitorable or a
 * Reportable depending on context).
 *
 * Hall Ticket generation, Seating Arrangement, and the Monitoring
 * Dashboard itself are Phase 2 features — this class only stores the
 * core exam data and exposes basic status/summary information.
 */
public class Exam implements Monitorable, Reportable, Serializable {

    private static final long serialVersionUID = 1L;

    private String examId;
    private Subject subject;
    private Hall hall;
    private LocalDate examDate;
    private LocalTime startTime;
    private int durationMinutes;
    private String assignedFacultyId;
    private ExamStatus status;

    public Exam(String examId, Subject subject, Hall hall, LocalDate examDate,
                LocalTime startTime, int durationMinutes, String assignedFacultyId) {
        this.examId = examId;
        this.subject = subject;
        this.hall = hall;
        this.examDate = examDate;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
        this.assignedFacultyId = assignedFacultyId;
        this.status = ExamStatus.SCHEDULED;
    }

    // Overloaded constructor: allows creating an Exam without assigning
    // a faculty member up front (can be assigned later). Demonstrates
    // Constructor Overloading.
    public Exam(String examId, Subject subject, Hall hall, LocalDate examDate,
                LocalTime startTime, int durationMinutes) {
        this(examId, subject, hall, examDate, startTime, durationMinutes, null);
    }

    public String getExamId() {
        return examId;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getAssignedFacultyId() {
        return assignedFacultyId;
    }

    public void setAssignedFacultyId(String assignedFacultyId) {
        this.assignedFacultyId = assignedFacultyId;
    }

    public ExamStatus getStatus() {
        return status;
    }

    public void setStatus(ExamStatus status) {
        this.status = status;
    }

    /**
     * Implementation of Monitorable.
     */
    @Override
    public String getCurrentStatus() {
        return "Exam " + examId + " is currently " + status;
    }

    /**
     * Implementation of Reportable.
     */
    @Override
    public String generateSummary() {
        return examId + " | " + subject.getSubjectName() + " | " + examDate +
               " " + startTime + " | Hall: " + hall.getHallName() + " | Status: " + status;
    }

    @Override
    public String toString() {
        return generateSummary();
    }
}
