package enums;

/**
 * Represents attendance status for a student in an exam.
 * NOTE: The full Attendance Module itself is a Phase 2 feature,
 * but this enum is defined now (as required by the 40% scope)
 * so that Model classes referencing attendance concepts can
 * compile if needed in later phases without restructuring enums.
 */
public enum AttendanceStatus {
    PRESENT,
    ABSENT,
    NOT_MARKED
}
