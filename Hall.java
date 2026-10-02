package model;

import java.io.Serializable;

/**
 * Represents an examination hall/room. Seating Arrangement logic is a
 * Phase 2 feature; here Hall only stores basic identifying data so an
 * Exam can reference the venue it will be conducted in.
 */
public class Hall implements Serializable {

    private static final long serialVersionUID = 1L;

    private String hallId;
    private String hallName;
    private int capacity;

    public Hall(String hallId, String hallName, int capacity) {
        this.hallId = hallId;
        this.hallName = hallName;
        this.capacity = capacity;
    }

    public String getHallId() {
        return hallId;
    }

    public void setHallId(String hallId) {
        this.hallId = hallId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return hallName + " (Capacity: " + capacity + ")";
    }
}
