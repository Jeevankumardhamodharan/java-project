package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic file-handling utility built around Java Serialization.
 * All repositories (StudentRepository, FacultyRepository, ExamRepository)
 * delegate their persistence to this class, avoiding duplicated
 * File I/O code (DRY principle).
 *
 * Data files are stored in a top-level "data" directory (created
 * automatically on first use) as .dat files.
 */
public class FileUtil {

    private static final String DATA_DIRECTORY = "data";

    private FileUtil() {
    }

    /**
     * Ensures the data directory exists before any read/write attempt.
     */
    private static void ensureDataDirectoryExists() {
        File dir = new File(DATA_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private static String resolvePath(String fileName) {
        return DATA_DIRECTORY + File.separator + fileName;
    }

    /**
     * Serializes an entire List of objects to the given file, overwriting
     * any previous contents. Generic method: works for List<Student>,
     * List<Faculty>, List<Exam>, etc.
     */
    public static <T extends Serializable> void saveList(String fileName, List<T> list) {
        ensureDataDirectoryExists();
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(resolvePath(fileName)))) {
            oos.writeObject(list);
        } catch (IOException e) {
            System.out.println("Error saving data to " + fileName + ": " + e.getMessage());
        }
    }

    /**
     * Reads back a previously-saved List of objects. If the file does not
     * exist yet (first run), an empty list is returned instead of throwing.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> List<T> loadList(String fileName) {
        ensureDataDirectoryExists();
        File file = new File(resolvePath(fileName));
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data from " + fileName + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Appends a line of plain text to a .txt log-style file
     * (kept available for simple text-based logging needs, alongside
     * the Serialization-based storage used for entity records).
     */
    public static void appendTextLine(String fileName, String line) {
        ensureDataDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(resolvePath(fileName), true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to " + fileName + ": " + e.getMessage());
        }
    }
}
