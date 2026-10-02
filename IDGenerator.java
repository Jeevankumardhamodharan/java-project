package util;

/**
 * Generates simple, readable unique IDs prefixed by entity type
 * (e.g. STU0001, FAC0001, EXM0001). Uses an in-memory counter seeded
 * from the current size of the relevant collection each time the
 * repository asks for a new ID, so IDs stay sequential across runs.
 */
public class IDGenerator {

    // Private constructor: this is a static utility class and should
    // never be instantiated.
    private IDGenerator() {
    }

    public static String generateStudentId(int currentCount) {
        return String.format("STU%04d", currentCount + 1);
    }

    public static String generateFacultyId(int currentCount) {
        return String.format("FAC%04d", currentCount + 1);
    }

    public static String generateExamId(int currentCount) {
        return String.format("EXM%04d", currentCount + 1);
    }

    public static String generateAdminId(int currentCount) {
        return String.format("ADM%04d", currentCount + 1);
    }

    // Generic overload usable for Hall/Subject IDs or any future entity.
    // Demonstrates Method Overloading.
    public static String generateId(String prefix, int currentCount) {
        return String.format("%s%04d", prefix, currentCount + 1);
    }
}
