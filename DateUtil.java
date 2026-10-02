package util;

import exception.InvalidInputException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Wraps the java.time (Date & Time API) classes used across the project
 * so that date/time parsing and formatting rules live in one place.
 */
public class DateUtil {

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private DateUtil() {
    }

    public static LocalDate parseDate(String dateStr) throws InvalidInputException {
        try {
            return LocalDate.parse(dateStr.trim(), DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Invalid date format. Please use dd-MM-yyyy.");
        }
    }

    public static LocalTime parseTime(String timeStr) throws InvalidInputException {
        try {
            return LocalTime.parse(timeStr.trim(), TIME_FORMAT);
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Invalid time format. Please use HH:mm (24-hour).");
        }
    }

    public static String formatDate(LocalDate date) {
        return date == null ? "N/A" : date.format(DATE_FORMAT);
    }

    public static String formatTime(LocalTime time) {
        return time == null ? "N/A" : time.format(TIME_FORMAT);
    }

    public static LocalDate today() {
        return LocalDate.now();
    }

    /**
     * Checks whether a given date is not in the past (used when Admin
     * creates an Exam, so exams cannot be scheduled retroactively).
     */
    public static boolean isTodayOrFuture(LocalDate date) {
        return !date.isBefore(LocalDate.now());
    }
}
