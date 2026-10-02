package repository;

import model.Exam;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles persistence and in-memory management of Exam records.
 * Backed by Serialization via FileUtil (exams.dat).
 */
public class ExamRepository {

    private static final String FILE_NAME = "exams.dat";

    private List<Exam> exams;

    public ExamRepository() {
        this.exams = FileUtil.loadList(FILE_NAME);
    }

    private void persist() {
        FileUtil.saveList(FILE_NAME, exams);
    }

    public void addExam(Exam exam) {
        exams.add(exam);
        persist();
    }

    public List<Exam> getAllExams() {
        return new ArrayList<>(exams);
    }

    public int count() {
        return exams.size();
    }

    /**
     * Returns only the exams assigned to a specific faculty member.
     * Used by the Faculty menu's "View Assigned Exams" feature.
     */
    public List<Exam> findByFacultyId(String facultyId) {
        return exams.stream()
                .filter(e -> facultyId.equalsIgnoreCase(e.getAssignedFacultyId()))
                .collect(Collectors.toList());
    }
}
