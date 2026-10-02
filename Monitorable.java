package interfaces;

/**
 * Implemented by classes whose state can be checked/monitored at
 * runtime. The full "Monitoring Dashboard" is a Phase 2 feature —
 * this interface only defines the contract so the Exam model can
 * expose its current status now.
 */
public interface Monitorable {

    /**
     * Returns the current status of the implementing object as text.
     */
    String getCurrentStatus();
}
