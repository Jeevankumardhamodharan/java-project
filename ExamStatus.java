package enums;

/**
 * Represents the lifecycle status of an Exam within the system.
 * Only SCHEDULED, ONGOING, COMPLETED, and CANCELLED are used
 * in this phase (40%). Additional statuses may be added in
 * later phases when the monitoring/evaluation modules are built.
 */
public enum ExamStatus {
    SCHEDULED,
    ONGOING,
    COMPLETED,
    CANCELLED;

    /**
     * Indicates whether the exam can still be edited or deleted
     * by an Administrator. Once an exam is ONGOING or COMPLETED,
     * it should no longer be modifiable.
     */
    public boolean isEditable() {
        return this == SCHEDULED;
    }
}
