package util;

import exception.InvalidInputException;

import java.util.regex.Pattern;

/**
 * Centralised input validation used across the Authentication module
 * and the Admin/Faculty/Student menus. Throwing InvalidInputException
 * (a custom checked exception) forces calling code to handle bad input
 * explicitly, keeping invalid data out of the repositories.
 */
public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    private ValidationUtil() {
    }

    /**
     * Validates that a string field (e.g. name) is non-null and non-blank.
     */
    public static void validateNotEmpty(String value, String fieldName) throws InvalidInputException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty.");
        }
    }

    /**
     * Overloaded version that also enforces a minimum length.
     * Demonstrates Method Overloading.
     */
    public static void validateNotEmpty(String value, String fieldName, int minLength)
            throws InvalidInputException {
        validateNotEmpty(value, fieldName);
        if (value.trim().length() < minLength) {
            throw new InvalidInputException(fieldName + " must be at least " + minLength + " characters long.");
        }
    }

    public static void validateEmail(String email) throws InvalidInputException {
        validateNotEmpty(email, "Email");
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new InvalidInputException("Email format is invalid: " + email);
        }
    }

    public static void validatePassword(String password) throws InvalidInputException {
        validateNotEmpty(password, "Password");
        if (password.length() < 4) {
            throw new InvalidInputException("Password must be at least 4 characters long.");
        }
    }

    /**
     * Validates a positive integer (e.g. semester number, duration in minutes).
     */
    public static void validatePositiveInt(int value, String fieldName) throws InvalidInputException {
        if (value <= 0) {
            throw new InvalidInputException(fieldName + " must be a positive number.");
        }
    }

    /**
     * Simple roll-number / ID format check: only letters and digits allowed.
     */
    public static void validateAlphanumeric(String value, String fieldName) throws InvalidInputException {
        validateNotEmpty(value, fieldName);
        if (!value.trim().matches("^[a-zA-Z0-9]+$")) {
            throw new InvalidInputException(fieldName + " must contain only letters and digits.");
        }
    }
}
