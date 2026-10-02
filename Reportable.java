package interfaces;

/**
 * Implemented by any class that can produce a short textual summary
 * of itself for display purposes. The full "Reports Module" (detailed,
 * formatted reports) is a Phase 2 feature — this interface only
 * establishes the contract so Phase 1 classes (Student, Faculty, Exam)
 * can already expose a summary method.
 */
public interface Reportable {

    /**
     * Returns a concise, human-readable summary of the implementing object.
     */
    String generateSummary();
}
