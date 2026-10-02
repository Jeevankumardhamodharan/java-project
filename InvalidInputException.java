package exception;

/**
 * Thrown when a user provides input that fails validation rules
 * (e.g. empty name, malformed email, invalid roll number format).
 * This is a checked exception so callers are forced to handle it,
 * which keeps invalid data out of the repositories/files.
 */
public class InvalidInputException extends Exception {

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
