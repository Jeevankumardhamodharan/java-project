package repository;

import model.Faculty;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Handles persistence and in-memory management of Faculty records.
 * Backed by Serialization via FileUtil (faculty.dat).
 */
public class FacultyRepository {

    private static final String FILE_NAME = "faculty.dat";

    private List<Faculty> facultyList;

    public FacultyRepository() {
        this.facultyList = FileUtil.loadList(FILE_NAME);
    }

    private void persist() {
        FileUtil.saveList(FILE_NAME, facultyList);
    }

    public void addFaculty(Faculty faculty) {
        facultyList.add(faculty);
        persist();
    }

    public List<Faculty> getAllFaculty() {
        return new ArrayList<>(facultyList);
    }

    public int count() {
        return facultyList.size();
    }

    public Optional<Faculty> findByFacultyId(String facultyId) {
        return facultyList.stream()
                .filter(f -> f.getFacultyId().equalsIgnoreCase(facultyId))
                .findFirst();
    }

    public Optional<Faculty> findByEmail(String email) {
        return facultyList.stream()
                .filter(f -> f.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }
}
