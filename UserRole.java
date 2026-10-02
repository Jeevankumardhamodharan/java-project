package enums;

/**
 * Represents the role of a user within the ExamVision system.
 * Used during authentication to determine which menu/features
 * a logged-in user should be granted access to.
 */
public enum UserRole {
    ADMIN,
    FACULTY,
    STUDENT;

    /**
     * Returns a human-friendly display name for the role.
     * Demonstrates a custom method inside an enum.
     */
    public String getDisplayName() {
        switch (this) {
            case ADMIN:
                return "Administrator";
            case FACULTY:
                return "Faculty Member";
            case STUDENT:
                return "Student";
            default:
                return "Unknown";
        }
    }
}
