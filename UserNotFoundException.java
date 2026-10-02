package exception;

/**
 * Thrown when a lookup (by ID, roll number, or username) does not
 * match any existing Student, Faculty, or Admin record.
 * Used by repositories and the authentication service.
 */
public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
